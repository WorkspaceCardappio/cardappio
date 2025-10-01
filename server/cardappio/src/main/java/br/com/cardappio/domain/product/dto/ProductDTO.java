package br.com.cardappio.domain.product.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.cardappio.domain.category.Category;
import br.com.cardappio.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private UUID id;
    private String name;
    private BigDecimal price;
    private BigDecimal quantity;
    private Boolean active;
    private LocalDate expirationDate;
    private String image;
    private Category category;

    public ProductDTO(final Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.active = product.getActive();
        this.expirationDate = product.getExpirationDate();
        this.image = product.getImage();
        this.category = product.getCategory();
    }
}
