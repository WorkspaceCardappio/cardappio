package br.com.cardappio.domain.variable;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.cardappio.domain.product.Product;
import br.com.cardappio.domain.product.ProductRepository;
import br.com.cardappio.domain.product.dto.FlutterProductVariableDTO;
import br.com.cardappio.domain.variable.dto.ProductVariableDTO;
import br.com.cardappio.domain.variable.dto.ProductVariableToOrderDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductVariableService {

    private final ProductVariableRepository repository;
    private final ProductRepository productRepository;

    public List<ProductVariableToOrderDTO> findByProductIdToOrder(final UUID productId) {
        return repository.findByProductToOrder(productId);
    }

    public List<FlutterProductVariableDTO> findFlutterProductVariables(UUID idProduct) {

        return repository.findFlutterProductVariables(idProduct);
    }

    public void persistItems(final List<ProductVariableDTO> items) {

        if (items.isEmpty()) {
            return;
        }

        final Product product = productRepository.getReferenceById(items.getFirst().product());

        final List<ProductVariable> variablesToSave = items.stream()
                .map(variable -> ProductVariable.of(variable, product))
                .toList();

        repository.saveAll(variablesToSave);
    }
}
