package br.com.cardappio.domain.menu.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.domain.menu.MenuProduct;
import br.com.cardappio.domain.product.dto.ProductToMenuDTO;

public record MenuProductListDTO(

        UUID id,

        ProductToMenuDTO product,

        BigDecimal price,

        Boolean active

) {

        public MenuProductListDTO(final MenuProduct menuProduct) {
                this(
                        menuProduct.getId(),
                        new ProductToMenuDTO(menuProduct.getProduct()),
                        menuProduct.getPrice(),
                        menuProduct.getActive()
                );
        }

}
