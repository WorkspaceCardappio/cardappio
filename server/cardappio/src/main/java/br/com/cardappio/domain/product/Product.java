package br.com.cardappio.domain.product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.cardappio.domain.product.dto.ProductDTO;
import com.cardappio.core.entity.EntityModel;

import br.com.cardappio.domain.category.Category;
import br.com.cardappio.utils.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Product implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotBlank(message = Messages.EMPTY_NAME)
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    @Column
    @NotNull
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal price;

    @Column
    @NotNull
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal quantity;

    @Column
    private String description;

    @Column
    private Boolean active = Boolean.TRUE;

    @Column
    @Future(message = Messages.FUTURE_DATE)
    private LocalDate expirationDate;

    @Column
    @Length(max = 255, message = Messages.SIZE_255)
    private String image;

    @Column
    private String note;

    @ManyToOne
    @NotNull
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductIngredient> productIngredients = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("parent")
    private List<Product> additional = new ArrayList<>();

    public static Product of(final ProductDTO dto){

        final Product product = new Product();
        product.setId(dto.id());
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setDescription(dto.description());
        product.setActive(dto.active());
        product.setCategory(dto.category());
        product.setExpirationDate(dto.expirationDate());
        product.setImage(dto.image());
        product.setNote(dto.note());
        return product;
    }

    public static Product of(final UUID id) {
        final Product product = new Product();
        product.setId(id);
        return product;
    }
}
