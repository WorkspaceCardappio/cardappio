package br.com.cardappio.integration.ticket.divider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import br.com.cardappio.domain.restaurant.RestaurantRepository;
import br.com.cardappio.domain.ticket.divider.DividerService;
import br.com.cardappio.integration.IntegrationTestBase;

@SpringBootTest
@Sql(scripts = {
        "classpath:inserts/address/insert.sql",
        "classpath:inserts/person/insert.sql",
        "classpath:inserts/restaurant/insert.sql",
        "classpath:inserts/category/insert.sql",
        "classpath:inserts/divider_ticket/insert.sql",
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class DividerServiceTest extends IntegrationTestBase {

    @Autowired
    private DividerService service;

    @Autowired
    private RestaurantRepository repository;

    @Test
    void divider() {

    }

}
