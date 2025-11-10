package br.com.cardappio.domain.product;

import br.com.cardappio.domain.product.dto.ProductDTO;
import br.com.cardappio.domain.product.dto.ProductItemDTO;
import br.com.cardappio.domain.product.dto.ProductListDTO;
import br.com.cardappio.domain.product.dto.ProductToMenuDTO;
import com.cardappio.core.controller.CrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController extends CrudController<Product, UUID, ProductListDTO, ProductDTO> {

    private final ProductService service;

    @GetMapping("/to-menu")
    public List<ProductToMenuDTO> findToMenu(@RequestParam(value = "search", defaultValue = "") final String search) {
        return service.findToMenu(search);
    }

    @GetMapping("/{id}/options")
    public List<ProductItemDTO> findOptionsById(@PathVariable final UUID id) {
        return service.findOptionsById(id);
    }

}
