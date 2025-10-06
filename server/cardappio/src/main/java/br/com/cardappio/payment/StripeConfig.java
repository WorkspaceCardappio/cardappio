package br.com.cardappio.payment;

import com.stripe.Stripe;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class StripeConfig {

    @PostConstruct
    public void init(){
        Dotenv dotenv = Dotenv.load();
        String secretKey = dotenv.get("STRIPE_SECRET_KEY");

        if (secretKey == null || secretKey.isBlank()) {
            throw new IllegalStateException("Variável de ambiente STRIPE_SECRET_KEY não definida!");
        }

        Stripe.apiKey = secretKey;
    }
}
