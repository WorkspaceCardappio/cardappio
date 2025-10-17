package br.com.cardappio.domain.category;

import com.cardappio.core.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {

    @Query("""
                    SELECT c.image
                    FROM Category c
                    WHERE c.id = :id
            """)
    String findImageById(UUID id);
}
