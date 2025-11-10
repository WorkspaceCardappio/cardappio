package br.com.cardappio.domain.additional;

import br.com.cardappio.domain.order.dto.ProductAdditionalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/additionals")
public class AdditionalController {

    private final AdditionalService service;

    @GetMapping("/product/{id}")
    public List<ProductAdditionalDTO> findByProductToOrder(@PathVariable final UUID id) {
        return service.findByProductIdToOrder(id);
    }

}
