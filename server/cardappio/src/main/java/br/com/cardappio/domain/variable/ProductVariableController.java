package br.com.cardappio.domain.variable;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cardappio.domain.product.dto.FlutterProductVariableDTO;
import br.com.cardappio.domain.variable.dto.ProductVariableDTO;
import br.com.cardappio.domain.variable.dto.ProductVariableToOrderDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/variables")
public class ProductVariableController {

    private final ProductVariableService service;

    @GetMapping("/product/{id}")
    public List<ProductVariableToOrderDTO> findByProductToOrder(@PathVariable final UUID id) {
        return service.findByProductIdToOrder(id);
    }

    @GetMapping("/{idProduct}/flutter-product-variables")
    public ResponseEntity<List<FlutterProductVariableDTO>> findFlutterProductVariables(@PathVariable UUID idProduct) {

        return ResponseEntity.ok(service.findFlutterProductVariables(idProduct));
    }

    @PostMapping("/persist")
    public void persistItems(@RequestBody @Valid List<ProductVariableDTO> items) {
        service.persistItems(items);
    }
}
