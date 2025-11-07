package br.com.cardappio.payment.service;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.OrderRepository;
import br.com.cardappio.enums.OrderStatus;
import br.com.cardappio.payment.dto.AbacatePayRequestDTO;
import br.com.cardappio.payment.dto.AbacatePixResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class AbacatePayService {

    @Value("${abacatepay.api.key}")
    private String apiKey;
    @Value("${abacatepay.api.base-url}")
    private String baseUrl;
    @Value("${abacatepay.api.return-url}")
    private String returnUrl;
    @Value("${abacatepay.api.completion-url}")
    private String completionUrl;
    @Value("${abacatepay.api.webhook.url}")
    private String webhookUrl;
    @Value("${abacatepay.api.webhook.secret}")
    private String webhookSecret;

    @Autowired private OrderRepository orderRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String PIX_QRCODE_ENDPOINT = "/pixQrCode/create";

    public AbacatePixResponseDTO createPixBilling(AbacatePayRequestDTO request) {

        Map<String, Object> customerData = Map.of(
                "name", request.customerName(),
                "email", request.customerEmail(),
                "taxId", request.customerTaxId(),
                "cellphone", request.customerCellphone()
        );

        Map<String, Object> body = Map.of(
                "amount", request.amount().multiply(new BigDecimal(100)).intValue(),
                "description", request.description(),
                "expiresIn", 3600,
                "customer", customerData,
                "metadata", Map.of(
                        "externalId", request.orderId().toString()
                ),
                "webhookUrl", webhookUrl
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        String url = baseUrl + PIX_QRCODE_ENDPOINT;

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                Map<String, Object> data = (Map<String, Object>) responseBody.get("data");

                String pixId = (String) data.get("id");

                Order order = orderRepository.findById(request.orderId())
                        .orElseThrow(() -> new RuntimeException("Pedido interno não encontrado."));
                order.setExternalReferenceId(pixId);
                orderRepository.save(order);

                return new AbacatePixResponseDTO(
                        pixId,
                        (String) data.get("status"),
                        (String) data.get("brCode"),
                        (String) data.get("brCodeBase64")
                );
            }
            throw new RuntimeException("Falha na API da Abacate Pay: " + response.getStatusCode());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar Pix na Abacate Pay: " + e.getMessage());
        }
    }

    public void processNotification(Map<String, Object> notificationPayload) {

        Map<String, Object> data = (Map<String, Object>) notificationPayload.get("data");

        String pixIdToSearch = null;
        String status = null;
        String eventId = (String) notificationPayload.get("id");

        if (data != null && data.containsKey("pixQrCode")) {

            Map<String, Object> pixQrCodeData = (Map<String, Object>) data.get("pixQrCode");

            pixIdToSearch = (String) pixQrCodeData.get("id");
            status = (String) pixQrCodeData.get("status");

            if (pixIdToSearch == null && pixQrCodeData.containsKey("payment")) {
                Map<String, Object> paymentData = (Map<String, Object>) pixQrCodeData.get("payment");
                pixIdToSearch = (String) paymentData.get("id");
                status = (String) paymentData.get("status");
            }
        }

        if (pixIdToSearch == null || status == null) {
            System.err.println("ALERTA CRÍTICO: Falha na extração. ID ou Status não encontrado. ID do Evento: " + eventId);
            return;
        }

        Optional<Order> orderOpt = orderRepository.findByExternalReferenceId(pixIdToSearch);

        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();

            if (order.getStatus() == OrderStatus.PENDING) {

                if ("PAID".equalsIgnoreCase(status)) {
                    order.setStatus(OrderStatus.PAID);
                    orderRepository.save(order);
                    System.out.println("SUCESSO: Cobrança " + pixIdToSearch + " APROVADA. Pedido atualizado para PAGO.");
                } else if ("REJECTED".equalsIgnoreCase(status) || "CANCELED".equalsIgnoreCase(status)) {
                    order.setStatus(OrderStatus.FAILED);
                    orderRepository.save(order);
                    System.out.println("AÇÃO: Cobrança " + pixIdToSearch + " Falhou/Cancelada. Pedido atualizado para FAILED.");
                } else {
                    System.out.println("AVISO: Status intermediário '" + status + "'.");
                }
            } else {
                System.out.println("AVISO: Pedido #" + order.getId() + " já estava processado. Ignorando notificação duplicada.");
            }
        } else {
            System.err.println("ALERTA CRÍTICO: PIX ID '" + pixIdToSearch + "' não encontrado no BD. Necessita auditoria.");
        }
    }
}