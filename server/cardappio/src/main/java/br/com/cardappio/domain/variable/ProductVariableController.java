package br.com.cardappio.domain.variable;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cardappio.domain.variable.dto.ProductVariableToOrderDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/variables")
public class ProductVariableController {

    private final ProductVariableService service;

    @GetMapping("/product/{id}")
    public List<ProductVariableToOrderDTO> findByProductToOrder(@PathVariable final UUID id) {
        return service.findByProductIdToOrder(id);
    }

}
