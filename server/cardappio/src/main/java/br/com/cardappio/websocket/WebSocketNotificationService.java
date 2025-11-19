package br.com.cardappio.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyOrderChange(String orderId, EventType eventType, Object data) {
        try {
            log.info("Notifying order change: {} - {}", orderId, eventType);
            OrderEvent event = OrderEvent.builder()
                    .orderId(orderId)
                    .eventType(eventType)
                    .timestamp(LocalDateTime.now())
                    .data(data)
                    .build();

            messagingTemplate.convertAndSend("/topic/orders", event);
            log.debug("Order notification sent successfully");
        } catch (Exception e) {
            log.error("Error sending order WebSocket notification for order {}: {}", orderId, e.getMessage(), e);
        }
    }

    public void notifyTicketChange(String ticketId, EventType eventType, Object data) {
        try {
            log.info("Notifying ticket change: {} - {}", ticketId, eventType);
            TicketEvent event = TicketEvent.builder()
                    .ticketId(ticketId)
                    .eventType(eventType)
                    .timestamp(LocalDateTime.now())
                    .data(data)
                    .build();

            messagingTemplate.convertAndSend("/topic/tickets", event);
            log.debug("Ticket notification sent successfully");
        } catch (Exception e) {
            log.error("Error sending ticket WebSocket notification for ticket {}: {}", ticketId, e.getMessage(), e);
        }
    }
}
