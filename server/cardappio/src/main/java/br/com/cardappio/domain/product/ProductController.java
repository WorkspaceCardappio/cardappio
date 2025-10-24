package br.com.cardappio.domain.product;

import br.com.cardappio.domain.product.dto.*;
import com.cardappio.core.controller.CrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
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

    @GetMapping("/{idCategory}/flutter-products")
    public ResponseEntity<List<FlutterProductDTO>> findFlutterProducts(@PathVariable UUID idCategory) {

        return ResponseEntity.ok(service.findFlutterProducts(idCategory));
    }

}
