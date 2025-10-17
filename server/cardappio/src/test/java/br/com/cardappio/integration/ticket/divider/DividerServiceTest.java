package br.com.cardappio.integration.ticket.divider;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.domain.ticket.TicketRepository;
import br.com.cardappio.domain.ticket.divider.DividerService;
import br.com.cardappio.domain.ticket.divider.dto.DividerOrdersDTO;
import br.com.cardappio.enums.TicketStatus;
import br.com.cardappio.integration.IntegrationTestBase;
import jakarta.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

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

        assertThatThrownBy(() -> service.ticket(UUID.fromString("fe410187-6662-4999-bc0c-2b1b1bea7e9e"), UUID.randomUUID(), new DividerOrdersDTO(Set.of())))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Ticket fe410187-6662-4999-bc0c-2b1b1bea7e9e not found");
    }

    @Test
    void exceptionWhenNotFoundOrder() {

        final UUID order = UUID.fromString("defba662-54a0-4b98-b2e6-8e4421aed15c");
        final UUID orderNotFound = UUID.fromString("fe410187-6662-4999-bc0c-2b1b1bea7e9e");

        final DividerOrdersDTO dividerValue = DividerOrdersDTO.builder()
                .orders(Set.of(order, orderNotFound))
                .build();

        final UUID idOrigin = UUID.fromString("defba662-54a0-4b98-b2e6-8e4421aed15c");
        final UUID idPerson = UUID.fromString("0ad8e87d-a9db-4746-823d-eeb7cd0efb10");

        assertThatThrownBy(() -> service.ticket(idOrigin, idPerson, dividerValue))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Order fe410187-6662-4999-bc0c-2b1b1bea7e9e not found");
    }

    @Test
    void exceptionWhenNotFoundPerson() {

        final UUID order = UUID.fromString("defba662-54a0-4b98-b2e6-8e4421aed15c");

        final DividerOrdersDTO dividerValue = DividerOrdersDTO.builder()
                .orders(Set.of(order))
                .build();

        final UUID idPerson = UUID.fromString("fb5836cd-aaab-47ab-9187-39f3e73d05d8");

        assertThatThrownBy(() -> service.ticket(order, idPerson, dividerValue))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format("Person %s not found", idPerson));
    }

    @Test
    void shouldPersistNewTicketWhenDivide() {

        final UUID orderOne = UUID.fromString("c69a173c-2156-4f49-b9d9-093b551e3099");
        final UUID orderTwo = UUID.fromString("80701206-d175-46af-aac1-f7afc5d82189");

        final DividerOrdersDTO dividerValue = DividerOrdersDTO.builder()
                .orders(Set.of(orderOne, orderTwo))
                .build();

        final UUID idPerson = UUID.fromString("0ad8e87d-a9db-4746-823d-eeb7cd0efb10");
        final UUID idOrigin = UUID.fromString("defba662-54a0-4b98-b2e6-8e4421aed15c");

        service.ticket(idOrigin, idPerson, dividerValue);

        final Optional<Ticket> ticketFound = ticketRepository.findByIdWithOrders(idOrigin);
        assertThat(ticketFound).isPresent();

        final Ticket ticket = ticketFound.get();

        assertThat(ticket.getOrders()).hasSize(1);
        assertThat(ticket.getNumber()).isEqualTo(1L);
        assertThat(ticket.getTotal()).isCloseTo(BigDecimal.valueOf(26.00), within(BigDecimal.valueOf(0.0001)));
        assertThat(ticket.getOrders()).extracting(Order::getId)
                .containsExactlyInAnyOrder(idOrigin);

        final Optional<Ticket> newTicketFound = ticketRepository.findByNumberWithOrder("2");
        assertThat(newTicketFound).isPresent();

        final Ticket newTicket = newTicketFound.get();
        assertThat(newTicket.getNumber()).isEqualTo(2L);
        assertThat(newTicket.getTotal()).isCloseTo(BigDecimal.valueOf(25.00), within(BigDecimal.valueOf(0.0001)));
        assertThat(newTicket.getStatus()).isEqualTo(TicketStatus.OPEN);
        assertThat(newTicket.getTable()).isEqualTo(ticket.getTable());
        assertThat(newTicket.getOrders()).hasSize(2);
        assertThat(newTicket.getOrders()).extracting(Order::getId)
                .containsExactlyInAnyOrder(UUID.fromString("c69a173c-2156-4f49-b9d9-093b551e3099"), UUID.fromString("80701206-d175-46af-aac1-f7afc5d82189"));
    }

}