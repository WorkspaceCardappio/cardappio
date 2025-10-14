package br.com.cardappio.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class ExceptionTestController {

    @GetMapping("/test/illegal-argument")
    public void throwIllegalArgument() {
        throw new IllegalArgumentException("invalid value");
    }

    @GetMapping("/test/not-found")
    public void throwNotFound() {
        throw new EntityNotFoundException("not found");
    }

    @GetMapping("/test/generic")
    public void throwGeneric() {
        throw new NullPointerException("null value");
    }
}