package br.com.cardappio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminConfig {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    public String getServerUrl() {
        return serverUrl;
    }

    public String getRealm() {
        return realm;
    }
}
