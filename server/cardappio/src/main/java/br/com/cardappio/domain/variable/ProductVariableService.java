package br.com.cardappio.domain.variable;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.cardappio.domain.variable.dto.ProductVariableToOrderDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductVariableService {

    private final ProductVariableRepository repository;

    public List<ProductVariableToOrderDTO> findByProductIdToOrder(final UUID productId) {
        return repository.findByProductToOrder(productId);
    }

}
