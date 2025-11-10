package br.com.cardappio.domain.product.item;

import br.com.cardappio.domain.product.item.dto.ProductItemDTO;
import br.com.cardappio.domain.product.item.dto.ProductItemListDTO;
import com.cardappio.core.controller.CrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/product-item")
@RequiredArgsConstructor
public class ProductItemController extends CrudController<ProductItem, UUID, ProductItemListDTO, ProductItemDTO> {
    
}
