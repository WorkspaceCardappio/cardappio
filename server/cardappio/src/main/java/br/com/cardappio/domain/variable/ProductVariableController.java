package br.com.cardappio.domain.variable;

import br.com.cardappio.domain.variable.dto.ProductVariableToOrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
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
