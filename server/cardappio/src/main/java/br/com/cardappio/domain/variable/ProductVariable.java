package br.com.cardappio.domain.variable;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.cardappio.core.entity.EntityModel;

import br.com.cardappio.domain.product.Product;
import br.com.cardappio.domain.product.dto.ProductVariableDTO;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal price = BigDecimal.ZERO;

    @Column
    private Boolean active = Boolean.TRUE;

    public static ProductVariable of(final ProductVariableDTO dto, final Product product) {

        final ProductVariable productVariable = new ProductVariable();
        productVariable.setId(dto.id());
        productVariable.setName(dto.name());

        return productVariable;
    }

    public static ProductVariable of(final UUID id) {

        final ProductVariable productVariable = new ProductVariable();
        productVariable.setId(id);

        return productVariable;
    }
}
