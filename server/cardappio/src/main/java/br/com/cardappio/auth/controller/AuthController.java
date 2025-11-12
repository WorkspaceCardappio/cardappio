package br.com.cardappio.auth.controller;

import br.com.cardappio.auth.dto.LoginRequest;
import br.com.cardappio.auth.dto.TokenResponse;
import br.com.cardappio.auth.service.KeycloakAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final KeycloakAuthService keycloakAuthService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            log.info("Recebida requisição de login para usuário: {}", loginRequest.getUsername());
            TokenResponse tokenResponse = keycloakAuthService.login(loginRequest);
            return ResponseEntity.ok(tokenResponse);
        } catch (RuntimeException e) {
            log.error("Erro ao processar login: {}", e.getMessage());

            if (e.getMessage().contains("Credenciais inválidas")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestParam String refreshToken) {
        try {
            log.info("Recebida requisição de refresh token");
            TokenResponse tokenResponse = keycloakAuthService.refreshToken(refreshToken);
            return ResponseEntity.ok(tokenResponse);
        } catch (RuntimeException e) {
            log.error("Erro ao processar refresh token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is running");
    }
}
