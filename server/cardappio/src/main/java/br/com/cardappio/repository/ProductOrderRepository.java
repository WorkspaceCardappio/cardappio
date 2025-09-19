package br.com.cardappio.repository;

import br.com.cardappio.entity.ProductOrder;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductOrderRepository extends CrudRepository<ProductOrder, UUID> {
}
