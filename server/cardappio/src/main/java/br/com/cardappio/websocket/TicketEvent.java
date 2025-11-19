package br.com.cardappio.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketEvent {
    private String ticketId;
    private EventType eventType;
    private LocalDateTime timestamp;
    private Object data;
}
