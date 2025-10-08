package br.com.cardappio.domain.ticket.divider;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.OrderRepository;
import br.com.cardappio.domain.person.Person;
import br.com.cardappio.domain.person.PersonRepository;
import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.domain.ticket.TicketRepository;
import br.com.cardappio.domain.ticket.divider.dto.DividerDTO;
import br.com.cardappio.domain.ticket.divider.dto.IdDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DividerService {

    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;

    public void ticket(final DividerDTO valueToDivider) {

        final Ticket ticket = find(valueToDivider.origin().id());
        final List<Order> orders = getOrders(valueToDivider.orders());
        final Person person = findPerson(valueToDivider.person().id());

    }

    private Ticket find(final UUID ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ticket %s not found", ticketId)));
    }

    private Person findPerson(final UUID personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Person %s not found", personId)));
    }

    private List<Order> getOrders(final List<IdDTO> orders) {

        final Set<UUID> ordersIds = orders.stream().map(IdDTO::id).collect(Collectors.toSet());

        final Map<UUID, Order> orderById = orderRepository.findAllById(ordersIds)
                .stream()
                .collect(Collectors.toMap(Order::getId, Function.identity()));

        ordersIds.forEach(id -> {

            if (Objects.isNull(orderById.get(id))) {
                throw new EntityNotFoundException(String.format("Order %s not found", id));
            }
        });

        return orderById.values().stream().toList();

    }

}
