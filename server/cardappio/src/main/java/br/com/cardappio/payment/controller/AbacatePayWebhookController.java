package br.com.cardappio.payment.controller;

import br.com.cardappio.payment.service.AbacatePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/webhooks/abacatepay")
public class AbacatePayWebhookController {

    @Autowired
    private AbacatePayService abacatePayService;

    @PostMapping
    public ResponseEntity<Void> receiveAbacatePayNotification(@RequestBody Map<String, Object> notificationPayload) {

        String event = (String)  notificationPayload.getOrDefault("event", "UNKNOWN");
        System.out.println("✅ Notificação Abacate Pay Recebida. Evento: " + event);

        try {
            abacatePayService.processNotification(notificationPayload);
        } catch (Exception e) {
            System.err.println("⚠️ Erro fatal ao processar notificação MP: " + e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}