package br.com.cardappio.domain.order.additional;

import br.com.cardappio.domain.order.ProductOrderAdditional;
import br.com.cardappio.domain.order.additional.dto.OrderAdditionalDTO;
import com.cardappio.core.controller.CrudController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/order-additionals")
@RequiredArgsConstructor
public class OrderAdditionalController extends CrudController<ProductOrderAdditional, UUID, OrderAdditionalDTO, OrderAdditionalDTO> {

    private final OrderAdditionalService service;

    @PostMapping("/items")
    public ResponseEntity<Void> createItems(@RequestBody @Valid List<OrderAdditionalDTO> additionals) {
        service.createItems(additionals);
        return ResponseEntity.ok().build();
    }

}
