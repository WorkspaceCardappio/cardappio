package br.com.cardappio.payment.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cardappio.payment.service.AbacatePayService;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/webhooks/abacatepay")
public class AbacatePayWebhookController {

    @Autowired
    private AbacatePayService abacatePayService;

    @PostMapping(consumes = {"application/json", "application/x-www-form-urlencoded", "*/*"})
    public ResponseEntity<Void> receiveAbacatePayNotification(
            @RequestBody(required = false) Map<String, Object> notificationPayload,
            @RequestParam(required = false) String webhookSecret) {

        log.info("=== WEBHOOK ABACATE PAY RECEBIDO ===");
        log.info("WebhookSecret recebido: {}", webhookSecret);

        if (notificationPayload == null) {
            log.error("Payload vazio recebido");
            return ResponseEntity.ok().build();
        }

        String event = (String) notificationPayload.getOrDefault("event", "UNKNOWN");
        log.info("Notificação Abacate Pay Recebida. Evento: {}", event);

        try {
            abacatePayService.processNotification(notificationPayload);
            log.info("=== WEBHOOK PROCESSADO COM SUCESSO ===");
        } catch (Exception e) {
            log.error("Erro fatal ao processar notificação: {}", e.getMessage(), e);
        }

        return ResponseEntity.ok().build();
    }
}