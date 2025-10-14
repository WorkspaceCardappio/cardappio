package br.com.cardappio.domain.ticket.divider;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.OrderRepository;
import br.com.cardappio.domain.person.Person;
import br.com.cardappio.domain.person.PersonRepository;
import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.domain.ticket.TicketRepository;
import br.com.cardappio.domain.ticket.divider.dto.DividerOrdersDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DividerService {

    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;

    public void ticket(final UUID id, final UUID idPerson, final DividerOrdersDTO valueToDivider) {

        final Ticket ticket = find(id);
        final Person person = findPerson(idPerson);
        final List<Order> orders = getOrders(valueToDivider.orders());

        final Ticket newTicket = ticket.cloneAndIncrementNumber();

        ticket.getOrders().removeIf(t -> valueToDivider.orders().contains(t.getId()));
        ticket.setTotal(calculeTotal(ticket.getOrders()));

        newTicket.setOwner(person);
        orders.forEach(order -> order.setTicket(newTicket));
        newTicket.getOrders().addAll(orders);

        newTicket.setTotal(calculeTotal(newTicket.getOrders()));

        ticketRepository.saveAll(List.of(ticket, newTicket));
    }

    private Ticket find(final UUID ticketId) {
        return ticketRepository.findByIdWithOrders(ticketId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ticket %s not found", ticketId)));
    }

    private Person findPerson(final UUID personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Person %s not found", personId)));
    }

    private List<Order> getOrders(final Set<UUID> orders) {

        final Map<UUID, Order> orderById = orderRepository.findAllById(orders)
                .stream()
                .collect(Collectors.toMap(Order::getId, Function.identity()));

        orders.forEach(id -> {

            if (Objects.isNull(orderById.get(id))) {
                throw new EntityNotFoundException(String.format("Order %s not found", id));
            }
        });

        return orderById.values().stream().toList();

    }

    private BigDecimal calculeTotal(final List<Order> orders) {

        return orders
                .stream()
                .map(Order::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
