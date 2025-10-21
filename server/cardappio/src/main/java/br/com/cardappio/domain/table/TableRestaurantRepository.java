package br.com.cardappio.domain.table;

import com.cardappio.core.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TableRestaurantRepository extends CrudRepository<TableRestaurant, UUID> {
}
