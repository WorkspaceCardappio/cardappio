package br.com.cardappio.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import br.com.cardappio.config.PostgresTestContainerConfig;

@SpringBootTest
@Testcontainers
public abstract class IntegrationTestBase {

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        var container = PostgresTestContainerConfig.getInstance();
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }
}