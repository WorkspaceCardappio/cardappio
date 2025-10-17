package br.com.cardappio.domain.product;

import br.com.cardappio.domain.product.dto.ProductVariableDTO;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class ProductVariable implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotBlank(message = Messages.EMPTY_NAME)
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    @NotNull
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal price;

    @Column
    private Boolean active = Boolean.TRUE;

    public static ProductVariable of(final ProductVariableDTO dto, final Product product){

        final ProductVariable productVariable = new ProductVariable();
        productVariable.setId(dto.id());
        productVariable.setName(dto.name());

        return productVariable;
    }
}
