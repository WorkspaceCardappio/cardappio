package br.com.cardappio.domain.order.variable;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

import br.com.cardappio.domain.order.ProductOrderVariable;

@Repository
public interface OrderVariableRepository extends CrudRepository<ProductOrderVariable, UUID> {
}
