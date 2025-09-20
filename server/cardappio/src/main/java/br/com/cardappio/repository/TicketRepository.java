package br.com.cardappio.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

import br.com.cardappio.entity.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, UUID> {
}
