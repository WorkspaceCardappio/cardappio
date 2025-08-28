package br.com.cardappio.repository;

import br.com.cardappio.entity.Category;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
