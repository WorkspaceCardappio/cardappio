package br.com.cardappio.domain.ticket.split;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.OrderRepository;
import br.com.cardappio.domain.person.Person;
import br.com.cardappio.domain.person.PersonRepository;
import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.domain.ticket.TicketRepository;
import br.com.cardappio.domain.ticket.split.dto.SplitOrdersDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SplitService {

    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;

    public void ticket(final UUID id, final UUID idPerson, final SplitOrdersDTO valueToSplit) {

        final Ticket ticket = find(id);
        final Person person = findPerson(idPerson);
        final List<Order> orders = getOrders(valueToSplit.orders());

        validateSizeToSplit(ticket, orders);

        final Ticket newTicket = defineTicketToSend(ticket, valueToSplit.ticket());

        ticket.getOrders().removeIf(t -> valueToSplit.orders().contains(t.getId()));

        newTicket.setOwner(person);
        orders.forEach(order -> order.setTicket(newTicket));
        newTicket.getOrders().addAll(orders);

        ticketRepository.saveAll(List.of(ticket, newTicket));
    }

    private void validateSizeToSplit(final Ticket ticket, final List<Order> orders) {

        final int sizeOrders = ticket.getOrders().size();
        final int sizeOrdersToSplit = orders.size();

        if (sizeOrdersToSplit == sizeOrders) {
            throw new IllegalArgumentException("Não é permitido dividir todos os itens da comanda");
        }

    }

    private Ticket defineTicketToSend(final Ticket origin, final UUID ticketToSend) {

        if (Objects.isNull(ticketToSend)) {
            return origin.cloneByNewTicket();
        }

        return ticketRepository.findById(ticketToSend)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ticket %s not found", ticketToSend)));
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

}
