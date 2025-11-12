package br.com.cardappio.domain.product.item;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.product.item.dto.ProductItemDTO;
import br.com.cardappio.domain.product.item.dto.ProductItemListDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/product-additionals")
@RequiredArgsConstructor
public class ProductAdditionalontroller extends CrudController<ProductItem, UUID, ProductItemListDTO, ProductItemDTO> {

    private final ProductItemService service;

    @PostMapping("/persist")
    public void persistItems(@RequestBody @Valid List<ProductItemDTO> items) {
        service.persistItems(items);
    }

}
