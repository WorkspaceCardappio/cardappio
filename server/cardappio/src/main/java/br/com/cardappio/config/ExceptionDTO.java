package br.com.cardappio.config;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionDTO {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
}
