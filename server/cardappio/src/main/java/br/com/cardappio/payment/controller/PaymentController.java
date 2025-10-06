package br.com.cardappio.payment.controller;

import br.com.cardappio.payment.dto.PaymentRequest;
import br.com.cardappio.payment.dto.PaymentResponse;
import br.com.cardappio.payment.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;
    @Autowired
    private ServletRequest httpRequest;

    @PostMapping("/create")
    public ResponseEntity<?> createPaymentIntent(@Valid @RequestBody PaymentRequest request){
        boolean captchaOk = service.verifyHcaptcha(request.hcaptchaToken(), httpRequest.getRemoteAddr());
        if (!captchaOk) {
            return ResponseEntity.badRequest().body("Captcha inválido");
        }
        try {

            PaymentIntent paymentIntent = service.createPaymentIntent(request);

            PaymentResponse response = new PaymentResponse(
                    paymentIntent.getId(),
                    paymentIntent.getAmount(),
                    paymentIntent.getCurrency(),
                    service.mapStripeStatus(paymentIntent),
                    paymentIntent.getDescription(),
                    paymentIntent.getPaymentMethod(),
                    paymentIntent.getClientSecret()
            );
            System.out.println(response);
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
