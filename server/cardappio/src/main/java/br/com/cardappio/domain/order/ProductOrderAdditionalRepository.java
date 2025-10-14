package br.com.cardappio.domain.order;

import com.cardappio.core.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductOrderAdditionalRepository extends CrudRepository<ProductOrderAdditional, UUID> {
}
