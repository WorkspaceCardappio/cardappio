package br.com.cardappio.domain.order.item;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

import br.com.cardappio.domain.order.ProductOrder;

@Repository
public interface OrderItemRepository extends CrudRepository<ProductOrder, UUID> {
}
