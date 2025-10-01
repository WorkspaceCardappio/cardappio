package br.com.cardappio.domain.product;

import br.com.cardappio.domain.product.dto.ProductDTO;

import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController extends CrudController<Product, UUID, ProductDTO, ProductDTO> {
}
