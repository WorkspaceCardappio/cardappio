package br.com.cardappio.payment.controller;

import br.com.cardappio.payment.dto.AbacatePayRequestDTO;
import br.com.cardappio.payment.dto.AbacatePixResponseDTO;
import br.com.cardappio.payment.service.AbacatePayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class AbacatePayController {

    @Autowired
    private AbacatePayService abacatePayService;

    @PostMapping("/pix")
    public ResponseEntity<AbacatePixResponseDTO> createPixPayment(@RequestBody @Valid AbacatePayRequestDTO request) {

        try {
            AbacatePixResponseDTO response = abacatePayService.createPixBilling(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.err.println("Erro ao iniciar Pix Abacate Pay: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}