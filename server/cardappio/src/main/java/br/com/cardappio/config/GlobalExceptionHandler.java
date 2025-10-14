package br.com.cardappio.config;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> handleException(final Exception exception) {

        log.error("unexpected error:", exception);

        final ExceptionDTO internalServerError = ExceptionDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .error("Internal Server Error")
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(internalServerError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDTO> handleIllegalArgument(final IllegalArgumentException exception) {

        final ExceptionDTO badRequest = ExceptionDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(badRequest);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleNotFoundException(final EntityNotFoundException exception) {

        final ExceptionDTO badRequest = ExceptionDTO.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not found")
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(badRequest);
    }

}
