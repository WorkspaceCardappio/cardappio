package br.com.cardappio.domain.order.additional;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

import br.com.cardappio.domain.order.ProductOrderAdditional;

@Repository
public interface OrderAdditionalRepository extends CrudRepository<ProductOrderAdditional, UUID> {
}
