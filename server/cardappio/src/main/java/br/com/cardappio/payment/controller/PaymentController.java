package br.com.cardappio.payment.controller;

import br.com.cardappio.payment.dto.PaymentRequestDTO;
import br.com.cardappio.payment.service.PaymentService;
import com.stripe.model.PaymentIntent;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Import
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-payment")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@Valid @RequestBody PaymentRequestDTO request) {

        try {
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(request);

            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", paymentIntent.getClientSecret());

            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            System.err.println("Erro na comunicação com a Stripe: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Falha na criação da Intent de Pagamento: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (RuntimeException e) {
            System.err.println("Erro de Negócio: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
