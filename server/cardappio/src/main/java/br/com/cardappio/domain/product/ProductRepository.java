package br.com.cardappio.domain.product;

import com.cardappio.core.repository.CrudRepository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {
}
