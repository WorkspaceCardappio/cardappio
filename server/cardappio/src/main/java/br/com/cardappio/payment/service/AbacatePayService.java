package br.com.cardappio.payment.service;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.OrderRepository;
import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.domain.ticket.TicketRepository;
import br.com.cardappio.enums.OrderStatus;
import br.com.cardappio.enums.TicketStatus;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AbacatePayService {

    @Value("${abacatepay.api.key}")
    private String apiKey;
    @Value("${abacatepay.api.base-url}")
    private String baseUrl;
    @Value("${abacatepay.api.webhook.url}")
    private String webhookUrl;
    @Value("${abacatepay.api.webhook.secret}")
    private String webhookSecret;
    private static final String PIX_SIMULATE_ENDPOINT = "/pixQrCode/simulate-payment";

    @Autowired private OrderRepository orderRepository;
    @Autowired private TicketRepository  ticketRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String PIX_QRCODE_ENDPOINT = "/pixQrCode/create";

    public AbacatePixResponseDTO createPixBilling(AbacatePayRequestDTO request) {

        Ticket ticket = ticketRepository.findById(request.ticketId())
                .orElseThrow(() -> new RuntimeException("Comanda não encontrada."));

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<Order> pendingOrders = ticket.getOrders().stream()
                .filter(order -> order.getStatus() == OrderStatus.PENDING)
                .collect(Collectors.toList());

        if (pendingOrders.isEmpty()) {
            throw new RuntimeException("Não há pedidos pendentes para esta Comanda. Status: " + ticket.getStatus());
        }

        for (Order order : pendingOrders) {
            totalAmount = totalAmount.add(order.getTotal());
        }

        Map<String, Object> customerData = Map.of(
                "name", request.customerName(),
                "email", request.customerEmail(),
                "taxId", request.customerTaxId(),
                "cellphone", request.customerCellphone()
        );

        Map<String, Object> body = Map.of(
                "amount", totalAmount.multiply(new BigDecimal(100)).intValue(),
                "description", request.description(),
                "expiresIn", 3600,
                "customer", customerData,
                "metadata", Map.of(
                        "externalId", ticket.getId().toString()
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

                ticket.setExternalReferenceId(pixId);
                ticketRepository.save(ticket);

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

        Optional<Ticket> ticketOpt = ticketRepository.findByExternalReferenceId(pixIdToSearch);

        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();

            List<Order> pendingOrders = ticket.getOrders().stream()
                    .filter(order -> order.getStatus() == OrderStatus.PENDING)
                    .collect(Collectors.toList());

            if (!pendingOrders.isEmpty()) {

                if ("PAID".equalsIgnoreCase(status)) {
                    for (Order order : pendingOrders) {
                        order.setStatus(OrderStatus.PAID);
                        orderRepository.save(order);
                    }
                    ticket.setStatus(TicketStatus.FINISHED);
                    ticketRepository.save(ticket);

                    System.out.println("SUCESSO: Cobrança " + pixIdToSearch + " APROVADA. Comanda #" + ticket.getNumber() + " e Orders atualizados para PAGO/FINALIZADA.");

                } else if ("REJECTED".equalsIgnoreCase(status) || "CANCELED".equalsIgnoreCase(status)) {
                    for (Order order : pendingOrders) {
                        order.setStatus(OrderStatus.FAILED);
                        orderRepository.save(order);
                    }
                    System.out.println("AÇÃO: Cobrança " + pixIdToSearch + " Falhou/Cancelada. Orders atualizadas para FAILED.");

                } else {
                    System.out.println("AVISO: Status intermediário '" + status + "'.");
                }
            } else {
                System.out.println("AVISO: Comanda #" + ticket.getNumber() + " já estava com pedidos processados/pagos. Ignorando notificação duplicada.");
            }
        } else {
            System.err.println("ALERTA CRÍTICO: PIX ID '" + pixIdToSearch + "' não encontrado no BD. Necessita auditoria.");
        }
    }

    public void simulatePixPayment(String pixId) {

        Map<String, Object> body = Map.of("metadata", Map.of());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        String url = baseUrl + PIX_SIMULATE_ENDPOINT + "?id=" + pixId;

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("SUCESSO: Pagamento Pix simulado com sucesso. Aguardando notificação de webhook.");
            } else {
                throw new RuntimeException("Falha na API da Abacate Pay ao simular: Status " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao simular Pix na Abacate Pay: " + e.getMessage());
        }
    }
}