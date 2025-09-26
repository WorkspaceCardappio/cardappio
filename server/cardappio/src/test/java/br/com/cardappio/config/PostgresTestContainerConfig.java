package br.com.cardappio.config;

import org.testcontainers.containers.PostgreSQLContainer;

@SuppressWarnings("resource")
public class PostgresTestContainerConfig {

    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;

    static {

        POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:17")
                .withDatabaseName("dev")
                .withUsername("dev")
                .withPassword("dev")
                .withReuse(true);

        POSTGRESQL_CONTAINER.start();
    }

    public static PostgreSQLContainer<?> getInstance() {
        return POSTGRESQL_CONTAINER;
    }

}
