package br.com.cardappio.domain.order;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, UUID> {
}
