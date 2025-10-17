package br.com.cardappio.domain.additional;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.cardappio.core.entity.EntityModel;

import br.com.cardappio.domain.additional.dto.AdditionalDTO;
import br.com.cardappio.domain.product.Product;
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
