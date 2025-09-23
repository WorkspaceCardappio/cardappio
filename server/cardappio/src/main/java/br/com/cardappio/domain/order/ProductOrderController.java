package br.com.cardappio.domain.order;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.order.dto.ProductOrderDTO;

@RestController
@RequestMapping("/product-orders")
public class ProductOrderController extends CrudController<ProductOrder, UUID, ProductOrderDTO, ProductOrderDTO> {
}
