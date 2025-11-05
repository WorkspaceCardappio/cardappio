package br.com.cardappio.payment.service;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.OrderRepository;
import br.com.cardappio.enums.OrderStatus;
import br.com.cardappio.payment.dto.PaymentRequestDTO; // Importa o DTO
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired; // Import
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import

@Service
public class PaymentService {

    @Value("${stripe.secret}")
    private String secretKey;

    @Autowired
    private OrderRepository orderRepository;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Transactional
    public PaymentIntent createPaymentIntent(PaymentRequestDTO request) throws StripeException {

        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new RuntimeException("Pedido (Order) não encontrado: " + request.orderId()));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Este pedido não está pendente de pagamento.");
        }

        PaymentIntentCreateParams.AutomaticPaymentMethods autoPaymentMethods =
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                        .setEnabled(true)
                        .build();

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(request.amount())
                        .setCurrency(request.currency())
                        .setAutomaticPaymentMethods(autoPaymentMethods)
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        order.setPaymentIntentId(paymentIntent.getId());
        orderRepository.save(order);

        return paymentIntent;
    }

    public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException {
        return PaymentIntent.retrieve(paymentIntentId);
    }
}
