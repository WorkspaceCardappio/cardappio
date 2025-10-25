package br.com.cardappio.domain.order.variable;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.order.ProductOrderVariable;
import br.com.cardappio.domain.order.variable.dto.OrderVariableDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order-variables")
@RequiredArgsConstructor
public class OrderVariableController extends CrudController<ProductOrderVariable, UUID, OrderVariableDTO, OrderVariableDTO> {

    private final OrderVariableService service;

    @PostMapping("/items")
    public ResponseEntity<Void> createItems(@RequestBody @Valid List<OrderVariableDTO> variables) {
        service.createItems(variables);
        return ResponseEntity.ok().build();
    }

}
