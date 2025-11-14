package br.com.cardappio.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import br.com.cardappio.auth.service.KeycloakAuthService;
import br.com.cardappio.components.s3.S3StorageComponent;
import br.com.cardappio.config.PostgresTestContainerConfig;
import br.com.cardappio.config.S3Config;
import br.com.cardappio.config.SecurityConfig;
import br.com.cardappio.payment.service.AbacatePayService;

@SpringBootTest
@Testcontainers
public abstract class IntegrationTestBase {

    @MockitoBean
    private SecurityConfig securityConfig;

    @MockitoBean
    private KeycloakAuthService keycloakAuthService;

    @MockitoBean
    private S3Config s3Config;

    @MockitoBean
    private S3StorageComponent s3StorageComponent;

    @MockitoBean
    private AbacatePayService abacatePayService;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        var container = PostgresTestContainerConfig.getInstance();
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }
}