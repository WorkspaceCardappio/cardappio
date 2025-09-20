package br.com.cardappio.controller;

import br.com.cardappio.DTO.ProductOrderDTO;
import br.com.cardappio.domain.order.ProductOrder;
import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/product-orders")
public class ProductOrderController extends CrudController<ProductOrder, ProductOrderDTO, UUID> {
}
