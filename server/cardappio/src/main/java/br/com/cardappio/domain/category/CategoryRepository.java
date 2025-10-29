package br.com.cardappio.domain.category;

import br.com.cardappio.domain.category.dto.FlutterCategoryDTO;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {

    @Query("""
            SELECT DISTINCT new br.com.cardappio.domain.category.dto.FlutterCategoryDTO(c.id, c.name)
            FROM Category c
            JOIN Product p ON p.category = c
            JOIN MenuProduct mp ON mp.product = p
            JOIN mp.menu m
            WHERE c.active = true
            AND m.id = :idMenu
            """)
    List<FlutterCategoryDTO> findFlutterCategories(@Param("idMenu") UUID idMenu);
}
