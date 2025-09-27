package br.com.cardappio.domain.restaurant;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, UUID> {

}
