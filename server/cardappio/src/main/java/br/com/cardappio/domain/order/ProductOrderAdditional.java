package br.com.cardappio.domain.order;

import java.math.BigDecimal;
import java.util.UUID;

import com.cardappio.core.entity.EntityModel;

import br.com.cardappio.domain.additional.Additional;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "product_order_additional")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class ProductOrderAdditional implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = Messages.PRODUCT_NOT_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_order_id", nullable = false)
    private ProductOrder productOrder;

    @NotNull(message = Messages.PRODUCT_VARIABLE_NOT_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "additional_id", nullable = false)
    private Additional additional;

    @Column
    @NotNull(message = Messages.INGREDIENT_NOT_NULL)
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal quantity;

}
