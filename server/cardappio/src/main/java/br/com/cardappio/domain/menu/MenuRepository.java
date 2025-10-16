package br.com.cardappio.domain.menu;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import com.cardappio.core.repository.CrudRepository;

@Repository
public interface MenuRepository extends CrudRepository<Menu, UUID> {

    @Query("SELECT mp FROM MenuProduct mp WHERE mp.menu.id = :menuId")
    List<MenuProduct> findByMenuId(@Param("menuId") UUID menuId);

}
