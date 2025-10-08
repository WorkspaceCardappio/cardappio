package br.com.cardappio.integration.ticket.divider;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.domain.ticket.TicketRepository;
import br.com.cardappio.domain.ticket.divider.DividerService;
import br.com.cardappio.domain.ticket.divider.dto.DividerDTO;
import br.com.cardappio.domain.ticket.divider.dto.IdDTO;
import br.com.cardappio.enums.TicketStatus;
import br.com.cardappio.integration.IntegrationTestBase;
import jakarta.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = {
        "classpath:inserts/address/insert_address.sql",
        "classpath:inserts/person/insert_person.sql",
        "classpath:inserts/restaurant/insert_restaurant.sql",
        "classpath:inserts/table_restaurant/insert_table_restaurant.sql",
        "classpath:inserts/category/insert_category.sql",
        "classpath:inserts/divider_ticket/insert_divider.sql",
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class DividerServiceTest extends IntegrationTestBase {

    @Autowired
    private DividerService service;

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void ticketOriginNotFound() {

        final DividerDTO dividerValue = DividerDTO.builder()
                .origin(new IdDTO(UUID.fromString("fe410187-6662-4999-bc0c-2b1b1bea7e9e")))
                .build();

        assertThatThrownBy(() -> service.ticket(dividerValue))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Ticket fe410187-6662-4999-bc0c-2b1b1bea7e9e not found");
    }

    @Test
    void exceptionWhenNotFoundOrder() {

        final IdDTO order = new IdDTO(UUID.fromString("defba662-54a0-4b98-b2e6-8e4421aed15c"));
        final IdDTO orderNotFound = new IdDTO(UUID.fromString("fe410187-6662-4999-bc0c-2b1b1bea7e9e"));

        final DividerDTO dividerValue = DividerDTO.builder()
                .origin(new IdDTO(UUID.fromString("defba662-54a0-4b98-b2e6-8e4421aed15c")))
                .orders(List.of(order, orderNotFound))
                .build();

        assertThatThrownBy(() -> service.ticket(dividerValue))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Order fe410187-6662-4999-bc0c-2b1b1bea7e9e not found");
    }

    @Test
    void exceptionWhenNotFoundPerson() {

        final IdDTO order = new IdDTO(UUID.fromString("defba662-54a0-4b98-b2e6-8e4421aed15c"));

        final DividerDTO dividerValue = DividerDTO.builder()
                .origin(new IdDTO(UUID.fromString("defba662-54a0-4b98-b2e6-8e4421aed15c")))
                .orders(List.of(order))
                .person(new IdDTO(UUID.fromString("defba662-54a0-4b98-b2e6-8e4421aed15c")))
                .build();

        assertThatThrownBy(() -> service.ticket(dividerValue))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Person defba662-54a0-4b98-b2e6-8e4421aed15c not found");
    }

    @Test
    void shouldPersistNewTicketWhenDivide() {

        // TODO: ALTERAR PARA BUSCAR ORDER

        final IdDTO productOne = new IdDTO(UUID.fromString("4319b5ad-6b06-419d-b755-487dff1188c9"));
        final IdDTO productTwo = new IdDTO(UUID.fromString("36056118-41d0-4148-97ab-1cf3f46b0850"));

        final DividerDTO dividerValue = DividerDTO.builder()
                .origin(new IdDTO(UUID.fromString("defba662-54a0-4b98-b2e6-8e4421aed15c")))
                .orders(List.of(productOne, productTwo))
                .build();

        service.ticket(dividerValue);

        final Ticket ticket = ticketRepository.findById(UUID.fromString("defba662-54a0-4b98-b2e6-8e4421aed15c")).get();
        assertThat(ticket.getOrders()).hasSize(1);
        assertThat(ticket.getNumber()).isEqualTo(1L);
        assertThat(ticket.getTotal()).isEqualTo(BigDecimal.valueOf(25));

        final Optional<Ticket> newTicketFound = ticketRepository.findByNumber("2");
        assertThat(newTicketFound).isPresent();

        final Ticket newTicket = newTicketFound.get();
        assertThat(newTicket.getNumber()).isEqualTo(2L);
        assertThat(newTicket.getTotal()).isEqualTo(BigDecimal.valueOf(25));
        assertThat(newTicket.getStatus()).isEqualTo(TicketStatus.OPEN);
        assertThat(newTicket.getTable()).isEqualTo(ticket.getTable());
        assertThat(newTicket.getOrders()).hasSize(2);
        assertThat(newTicket.getOrders()).extracting(Order::getId)
                .containsExactlyInAnyOrder(UUID.fromString("c69a173c-2156-4f49-b9d9-093b551e3099"), UUID.fromString("80701206-d175-46af-aac1-f7afc5d82189"));


    }


}
