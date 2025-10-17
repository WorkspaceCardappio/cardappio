package br.com.cardappio.domain.additional;

import br.com.cardappio.domain.additional.dto.AdditionalDTO;
import br.com.cardappio.domain.category.Category;
import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Additional implements EntityModel<UUID> {

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

    @ManyToOne
    @NotNull
    @JoinColumn(name = "product_id")
    private Product product;

    public static Additional of(final AdditionalDTO dto, final Product product) {
        final Additional additional = new Additional();
        additional.setId(dto.id());
        additional.setName(dto.name());
        additional.setPrice(dto.price());
        additional.setProduct(product);
        return additional;
    }

    public static Additional of(final UUID id) {
        final Additional additional = new Additional();
        additional.setId(id);

        return additional;
    }
}
