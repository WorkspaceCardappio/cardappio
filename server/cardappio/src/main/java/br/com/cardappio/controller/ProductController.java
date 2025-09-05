package br.com.cardappio.controller;

import br.com.cardappio.DTO.ProductDTO;
import br.com.cardappio.entity.Product;
import com.cardappio.core.controller.CrudController;
import com.cardappio.core.service.CrudService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController extends CrudController<Product, ProductDTO, UUID> {


    public ProductController(final CrudService<Product, ProductDTO, UUID> service) {
        super(service);
    }
}
