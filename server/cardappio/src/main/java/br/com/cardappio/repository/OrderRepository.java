package br.com.cardappio.repository;

import br.com.cardappio.entity.Order;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {
}
