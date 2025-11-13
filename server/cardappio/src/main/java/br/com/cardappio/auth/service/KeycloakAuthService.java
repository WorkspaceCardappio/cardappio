package br.com.cardappio.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.cardappio.auth.dto.LoginRequest;
import br.com.cardappio.auth.dto.ServiceAccountLoginRequest;
import br.com.cardappio.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakAuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;


    public TokenResponse login(LoginRequest loginRequest) {
        try {
            String tokenEndpoint = String.format("%s/realms/%s/protocol/openid-connect/token",
                    keycloakUrl, realm);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "password");
            body.add("client_id", clientId);
            body.add("username", loginRequest.getUsername());
            body.add("password", loginRequest.getPassword());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            log.info("Tentando autenticar usuário: {}", loginRequest.getUsername());

            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                    tokenEndpoint,
                    request,
                    TokenResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                log.info("Usuário autenticado com sucesso: {}", loginRequest.getUsername());
                return response.getBody();
            } else {
                log.error("Falha na autenticação: Status {}", response.getStatusCode());
                throw new RuntimeException("Falha na autenticação");
            }

        } catch (HttpClientErrorException.Unauthorized e) {
            log.error("Usuário: {}", loginRequest.getUsername());
            throw new RuntimeException("Credenciais inválidas");
        } catch (HttpClientErrorException e) {
            log.error("Response body: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Erro HTTP: " + e.getStatusCode());
        } catch (Exception e) {
            log.error("Erro gerar ao autenticar usuário: {}", loginRequest.getUsername(), e);
            throw new RuntimeException("Erro ao processar login: " + e.getMessage());
        }
    }

    public TokenResponse refreshToken(String refreshToken) {
        try {
            String tokenEndpoint = String.format("%s/realms/%s/protocol/openid-connect/token",
                    keycloakUrl, realm);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "refresh_token");
            body.add("client_id", clientId);
            body.add("refresh_token", refreshToken);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                    tokenEndpoint,
                    request,
                    TokenResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                log.info("Token atualizado com sucesso");
                return response.getBody();
            } else {
                throw new RuntimeException("Falha ao atualizar token");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar token: " + e.getMessage());
        }
    }

    public TokenResponse serviceAccountLogin(ServiceAccountLoginRequest request) {
        try {
            String tokenEndpoint = String.format("%s/realms/%s/protocol/openid-connect/token",
                    keycloakUrl, realm);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "client_credentials");
            body.add("client_id", request.getClientId());
            body.add("client_secret", request.getClientSecret());

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

            log.info("Service Account login attempt - Client ID: {}", request.getClientId());

            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                    tokenEndpoint,
                    requestEntity,
                    TokenResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                log.info("Service Account authenticated successfully - Client ID: {}", request.getClientId());
                return response.getBody();
            } else {
                log.error("Service Account authentication failed: Status {}", response.getStatusCode());
                throw new RuntimeException("Falha na autenticação do service account");
            }

        } catch (HttpClientErrorException.Unauthorized e) {
            log.error("Service Account unauthorized - Client ID: {}", request.getClientId());
            throw new RuntimeException("Credenciais inválidas do service account");
        } catch (HttpClientErrorException e) {
            log.error("HTTP error during service account login: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Erro HTTP: " + e.getStatusCode());
        } catch (Exception e) {
            log.error("General error during service account login", e);
            throw new RuntimeException("Erro ao processar login do service account: " + e.getMessage());
        }
    }
}
