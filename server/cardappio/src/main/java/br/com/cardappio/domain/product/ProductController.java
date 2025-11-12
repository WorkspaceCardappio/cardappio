package br.com.cardappio.domain.product;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.product.dto.FlutterProductDTO;
import br.com.cardappio.domain.product.dto.ProductDTO;
import br.com.cardappio.domain.product.dto.ProductItemDTO;
import br.com.cardappio.domain.product.dto.ProductListDTO;
import br.com.cardappio.domain.product.dto.ProductToMenuDTO;
import lombok.RequiredArgsConstructor;

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

    @PostMapping("/{id}/finalize")
    public void finalize(@PathVariable final UUID id) {
        service.finalize(id);
    }

}
