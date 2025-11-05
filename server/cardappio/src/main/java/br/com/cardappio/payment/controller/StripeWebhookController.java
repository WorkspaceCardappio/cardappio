package br.com.cardappio.payment.controller;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.OrderRepository;
import br.com.cardappio.enums.OrderStatus;
import br.com.cardappio.payment.service.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/webhooks")
public class StripeWebhookController {

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    @Autowired
    private OrderRepository  orderRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            System.err.println("Webhook: Falha na verificação da assinatura!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assinatura inválida.");
        }

        StripeObject stripeObject = event.getDataObjectDeserializer().getObject()
                .orElseThrow(() -> new RuntimeException("Objeto de dados do evento não encontrado."));

        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntentSucceeded = (PaymentIntent) stripeObject;
                System.out.println("✅ Webhook: Pagamento BEM-SUCEDIDO para " + paymentIntentSucceeded.getId());

                this.handlePaymentSucceeded(paymentIntentSucceeded);
                break;

            case "payment_intent.payment_failed":
                PaymentIntent paymentIntentFailed = (PaymentIntent) stripeObject;
                System.out.println("❌ Webhook: Pagamento FALHOU para " + paymentIntentFailed.getId());

                this.handlePaymentFailed(paymentIntentFailed);
                break;
            default:
                System.out.println("Webhook: Evento não tratado: " + event.getType());
        }

        return ResponseEntity.ok("Recebido.");
    }

    private void handlePaymentSucceeded(PaymentIntent paymentIntent) {
        Optional<Order> orderOpt = orderRepository.findByPaymentIntentId(paymentIntent.getId());

        if (orderOpt.isEmpty()) {
            System.err.println("Webhook (Sucesso): Pedido não encontrado com o PaymentIntent ID: " + paymentIntent.getId());
            return;
        }

        Order order = orderOpt.get();

        if (order.getStatus() == OrderStatus.PAID) {
            System.out.println("Webhook (Sucesso): Pedido " + order.getId() + " já estava PAGO.");
            return;
        }
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
        System.out.println("Pedido " + order.getId() + " atualizado para PAGO.");
    }

    private void handlePaymentFailed(PaymentIntent paymentIntent) {
        Optional<Order> orderOpt = orderRepository.findByPaymentIntentId(paymentIntent.getId());

        if (orderOpt.isEmpty()) {
            System.err.println("Webhook (Falha): Pedido não encontrado com o PaymentIntent ID: " + paymentIntent.getId());
            return;
        }

        Order order = orderOpt.get();

        order.setStatus(OrderStatus.FAILED);
        orderRepository.save(order);
        System.out.println("Pedido " + order.getId() + " atualizado para FALHOU.");
    }
}
