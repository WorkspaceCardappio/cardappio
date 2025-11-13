package br.com.cardappio.payment.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cardappio.payment.service.AbacatePayService;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/api/webhooks/abacatepay")
public class AbacatePayWebhookController {

    @Autowired
    private AbacatePayService abacatePayService;

    @PostMapping
    public ResponseEntity<Void> receiveAbacatePayNotification(@RequestBody Map<String, Object> notificationPayload) {

        String event = (String)  notificationPayload.getOrDefault("event", "UNKNOWN");
        log.info(" Notificação Abacate Pay Recebida. Evento: {}", event);

        try {
            abacatePayService.processNotification(notificationPayload);
        } catch (Exception e) {
            log.error("Erro fatal ao processar notificação MP: {}", e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}