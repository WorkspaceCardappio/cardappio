package br.com.cardappio.domain.order;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.order.dto.OrderDTO;

@RestController
@RequestMapping("/api/orders")
public class OrderController extends CrudController<Order, UUID, OrderDTO, OrderDTO> {
}
