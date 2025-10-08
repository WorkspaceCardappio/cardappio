package br.com.cardappio.domain.additional;

import br.com.cardappio.domain.additional.dto.ProductOrderAdditionalDTO;
import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products-order-additional")
public class ProductOrderAdditionalController extends CrudController<ProductOrderAdditional, UUID, ProductOrderAdditionalDTO, ProductOrderAdditionalDTO> {
}
