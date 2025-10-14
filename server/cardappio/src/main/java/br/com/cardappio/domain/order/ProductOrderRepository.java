package br.com.cardappio.domain.order;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, UUID> {
}
