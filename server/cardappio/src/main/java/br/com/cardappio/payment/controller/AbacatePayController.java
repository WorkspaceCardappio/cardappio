package br.com.cardappio.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.cardappio.payment.dto.AbacatePayRequestDTO;
import br.com.cardappio.payment.dto.AbacatePixResponseDTO;
import br.com.cardappio.payment.service.AbacatePayService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
            log.error("Erro ao iniciar Pix Abacate Pay: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/pix/simulate/{pixId}")
    public ResponseEntity<Void> simulatePix(@PathVariable String pixId) {
        System.out.println("SIMULAÇÃO: Recebendo solicitação para simular PIX ID: " + pixId);

        try {
            abacatePayService.simulatePixPayment(pixId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            System.err.println("Erro ao simular Pix Abacate Pay: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}