package br.com.cardappio.domain.menu;

import org.springframework.stereotype.Repository;

import java.util.UUID;

import com.cardappio.core.repository.CrudRepository;

@Repository
public interface MenuRepository extends CrudRepository<Menu, UUID> {

}
