package br.com.cardappio.domain.product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.cardappio.domain.product.dto.ProductDTO;
import com.cardappio.core.entity.EntityModel;

import br.com.cardappio.domain.category.Category;
import br.com.cardappio.utils.Messages;

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
    private Boolean active = Boolean.TRUE;

    @Column
    @Future(message = Messages.FUTURE_DATE)
    private LocalDate expirationDate;

    @Column
    @Length(max = 255, message = Messages.SIZE_255)
    private String image;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "category_id")
    private Category category;

    public static Product of(final ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.id());
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setActive(dto.active());
        product.setExpirationDate(dto.expirationDate());
        product.setImage(dto.image());
        product.setCategory(dto.category());
        return product;
    }
}
