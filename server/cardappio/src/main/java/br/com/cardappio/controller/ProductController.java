package br.com.cardappio.controller;

import br.com.cardappio.DTO.ProductDTO;
import br.com.cardappio.entity.Product;
import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController extends CrudController<Product, UUID, ProductDTO, ProductDTO> {
}
