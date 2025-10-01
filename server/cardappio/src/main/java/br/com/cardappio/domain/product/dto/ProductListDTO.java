package br.com.cardappio.domain.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDTO {

    private UUID id;
    private String name;
    private BigDecimal price;
    private Boolean active;
    private Category category;
}
