package br.com.cardappio.domain.menu;

<<<<<<< Updated upstream
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
=======
import br.com.cardappio.domain.menu.dto.FlutterMenuDTO;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
>>>>>>> Stashed changes
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuRepository extends CrudRepository<Menu, UUID> {

<<<<<<< Updated upstream
    @Query("SELECT mp FROM MenuProduct mp WHERE mp.menu.id = :menuId")
    List<MenuProduct> findByMenuId(@Param("menuId") UUID menuId);

=======
    @Query("""
            SELECT new br.com.cardappio.domain.menu.dto.FlutterMenuDTO(m.id, m.name)
            FROM Menu m
            """)
    List<FlutterMenuDTO> getFlutterMenus();
>>>>>>> Stashed changes
}
