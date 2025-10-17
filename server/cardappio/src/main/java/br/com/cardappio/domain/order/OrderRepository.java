package br.com.cardappio.domain.order;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {

    Page<Order> findByTicketId(UUID ticketId, Pageable pageable);

}
