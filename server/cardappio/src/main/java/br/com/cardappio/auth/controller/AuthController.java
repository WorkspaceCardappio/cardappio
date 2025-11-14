package br.com.cardappio.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cardappio.auth.dto.LoginRequest;
import br.com.cardappio.auth.dto.ServiceAccountLoginRequest;
import br.com.cardappio.auth.dto.TokenResponse;
import br.com.cardappio.auth.service.KeycloakAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
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

    @PostMapping("/service-account/login")
    public ResponseEntity<TokenResponse> serviceAccountLogin(@Valid @RequestBody ServiceAccountLoginRequest request) {
        try {
            log.info("Service account login request - Client ID: {}", request.getClientId());
            TokenResponse tokenResponse = keycloakAuthService.serviceAccountLogin(request);
            return ResponseEntity.ok(tokenResponse);
        } catch (RuntimeException e) {
            log.error("Service account login failed - Client ID: {}, Error: {}",
                    request.getClientId(), e.getMessage());

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
