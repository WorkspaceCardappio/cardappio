package br.com.cardappio.repository;

import br.com.cardappio.entity.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MenuRepository extends CrudRepository<Menu, UUID> {

}
