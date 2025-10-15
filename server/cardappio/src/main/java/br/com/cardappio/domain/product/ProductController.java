package br.com.cardappio.domain.product;

import br.com.cardappio.domain.product.dto.ProductDTO;
import br.com.cardappio.domain.product.dto.ProductToMenuDTO;
import lombok.RequiredArgsConstructor;

import com.cardappio.core.controller.CrudController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController extends CrudController<Product, UUID, ProductDTO, ProductDTO> {

    private final ProductService service;

    @GetMapping("/to-menu")
    public List<ProductToMenuDTO> findToMenu(@RequestParam(value = "search",defaultValue = "") final String search) {
        return service.findToMenu(search);
    }

}
