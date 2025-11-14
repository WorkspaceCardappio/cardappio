package br.com.cardappio.domain.product.item;

import com.cardappio.core.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductItemRepository extends CrudRepository<ProductItem, UUID> {
}
