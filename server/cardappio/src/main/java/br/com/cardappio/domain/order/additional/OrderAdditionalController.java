package br.com.cardappio.domain.order.additional;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.order.ProductOrderAdditional;
import br.com.cardappio.domain.order.additional.dto.OrderAdditionalDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
