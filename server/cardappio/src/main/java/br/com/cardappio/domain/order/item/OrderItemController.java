package br.com.cardappio.domain.order.item;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.domain.order.item.dto.OrderItemDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController extends CrudController<ProductOrder, UUID, OrderItemDTO, OrderItemDTO> {
}
