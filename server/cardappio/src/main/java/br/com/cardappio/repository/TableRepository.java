package br.com.cardappio.repository;

import br.com.cardappio.entity.TableRestaurant;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TableRepository extends CrudRepository<TableRestaurant, UUID> {
}
