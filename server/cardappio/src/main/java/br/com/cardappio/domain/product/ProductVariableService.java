package br.com.cardappio.domain.product;

import br.com.cardappio.domain.product.dto.FlutterProductVariableDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductVariableService {

    private final ProductVariableRepository repository;

    public List<FlutterProductVariableDTO> findFlutterProductVariables(UUID idProduct) {

        return repository.findFlutterProductVariables(idProduct);
    }
}
