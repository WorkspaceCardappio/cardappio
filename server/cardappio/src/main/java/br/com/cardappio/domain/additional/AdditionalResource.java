package br.com.cardappio.domain.additional;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cardappio.domain.order.dto.ProductAdditionalDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/additionals")
public class AdditionalResource {

    private final AdditionalService service;

    @GetMapping("/product/{id}")
    public List<ProductAdditionalDTO> findByProductToOrder(@PathVariable final UUID productId) {
        return service.findByProductIdToOrder(productId);
    }

}
