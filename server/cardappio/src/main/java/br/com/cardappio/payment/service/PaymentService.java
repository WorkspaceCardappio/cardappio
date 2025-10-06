package br.com.cardappio.payment.service;

import br.com.cardappio.payment.dto.PaymentRequest;
import br.com.cardappio.payment.enums.PaymentStatus;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PaymentService {
    public boolean verifyHcaptcha(String token, String remoteIp) {
        String secret = "0x0000000000000000000000000000000000000000";
        RestTemplate rt = new RestTemplate();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("secret", secret);
        body.add("response", token);
        if (remoteIp != null) body.add("remoteip", remoteIp);

        Map<String, Object> resp = rt.postForObject("https://hcaptcha.com/siteverify", body, Map.class);
        return resp != null && Boolean.TRUE.equals(resp.get("success"));
    }

    public PaymentIntent createPaymentIntent(PaymentRequest request) throws StripeException {
        var params = PaymentIntentCreateParams.builder()
                .setAmount(request.amount())
                .setCurrency(request.currency())
                .setDescription(request.description())
                .setPaymentMethod(request.paymentMethodId())
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER).build()
                )
                .build();

        return PaymentIntent.create(params);
    }

    public PaymentStatus mapStripeStatus(PaymentIntent intent) {
        return switch (intent.getStatus()) {
            case "requires_payment_method" -> PaymentStatus.REQUIRES_PAYMENT_METHOD;
            case "requires_confirmation" -> PaymentStatus.REQUIRES_CONFIRMATION;
            case "requires_action" -> PaymentStatus.REQUIRES_ACTION;
            case "processing" -> PaymentStatus.PROCESSING;
            case "succeeded" -> PaymentStatus.SUCCEEDED;
            case "canceled" -> PaymentStatus.CANCELED;
            default -> throw new IllegalArgumentException("Status Stripe desconhecido: " + intent.getStatus());
        };
    }
}
