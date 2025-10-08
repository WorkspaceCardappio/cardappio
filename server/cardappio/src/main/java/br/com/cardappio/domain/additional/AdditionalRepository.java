package br.com.cardappio.domain.additional;

import com.cardappio.core.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdditionalRepository extends CrudRepository<Additional, UUID> {
}
