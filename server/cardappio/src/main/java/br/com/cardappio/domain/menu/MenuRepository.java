package br.com.cardappio.domain.menu;

import br.com.cardappio.domain.menu.dto.FlutterMenuDTO;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuRepository extends CrudRepository<Menu, UUID> {

    @Query("SELECT mp FROM MenuProduct mp WHERE mp.menu.id = :menuId")
    List<MenuProduct> findByMenuId(@Param("menuId") UUID menuId);

    @Query("""
            SELECT new br.com.cardappio.domain.menu.dto.FlutterMenuDTO(m.id, m.name)
            FROM Menu m
            WHERE m.active = true
            AND m.restaurant.id = :idRestaurant
            """)
    List<FlutterMenuDTO> findFlutterMenus(@Param("idRestaurant") UUID idRestaurant);
}
