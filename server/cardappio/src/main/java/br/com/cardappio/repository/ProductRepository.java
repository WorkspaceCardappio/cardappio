package br.com.cardappio.repository;

import br.com.cardappio.entity.Product;
import com.cardappio.core.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
}
