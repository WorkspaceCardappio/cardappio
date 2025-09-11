package br.com.cardappio.entity;

import br.com.cardappio.DTO.ProductDTO;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.cardappio.core.entity.EntityModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotNull(message = Messages.EMPTY_NAME)
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    @Column
    private BigDecimal price;

    @Column
    @Min(value = 0, message = Messages.MAIOR_QUE_ZERO)
    private BigDecimal quantity;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active = true;

    @Column
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private LocalDate expirationDate;

    @Column
    private String image;

    @Column
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductIngredient> productIngredients;

    public static Product of(final ProductDTO dto) {
        final Product product = new Product();

        product.setId(dto.id());
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setActive(dto.active());
        product.setCategory(dto.category());
        product.setExpirationDate(dto.expirationDate());
        product.setImage(dto.image());
        product.setProductIngredients(dto.productIngredients());

        return product;
    }

}
