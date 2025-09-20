package br.com.cardappio.domain.order;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

@Repository
public interface ProductOrderRepository extends CrudRepository<ProductOrder, UUID> {
}
