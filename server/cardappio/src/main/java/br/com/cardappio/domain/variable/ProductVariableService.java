package br.com.cardappio.domain.variable;

import br.com.cardappio.domain.product.dto.FlutterProductVariableDTO;
import br.com.cardappio.domain.variable.dto.ProductVariableToOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductVariableService {

    private final ProductVariableRepository repository;

    public List<ProductVariableToOrderDTO> findByProductIdToOrder(final UUID productId) {
        return repository.findByProductToOrder(productId);
    }

    public List<FlutterProductVariableDTO> findFlutterProductVariables(UUID idProduct) {

        return repository.findFlutterProductVariables(idProduct);
    }

}
