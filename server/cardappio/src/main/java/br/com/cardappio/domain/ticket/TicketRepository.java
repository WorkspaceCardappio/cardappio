package br.com.cardappio.domain.ticket;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, UUID> {

    Optional<Ticket> findByNumber(String number);

}
