package br.com.cardappio.controller;

import br.com.cardappio.DTO.OrderDTO;
import br.com.cardappio.domain.order.Order;
import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController extends CrudController<Order, OrderDTO, UUID> {
}
