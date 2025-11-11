package br.com.cardappio.domain.ingredient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.cardappio.core.entity.EntityModel;

import br.com.cardappio.domain.ingredient.dto.IngredientStockDTO;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class IngredientStock implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @NotNull(message = Messages.EMPTY_PRODUCT)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(nullable = false)
    @NotNull(message = Messages.EMPTY_NUMBER)
    private Long number;

    @NotNull(message = Messages.QUANTITY_NOT_NULL)
    @Column(nullable = false)
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal quantity = BigDecimal.ZERO;

    @Column(name = "expiration_date", nullable = false)
    @Future(message = Messages.EXPIRATION_DATE_NOT_PAST)
    @NotNull(message = Messages.EXPIRATION_DATE_NOT_NULL)
    private LocalDate expirationDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    public static IngredientStock of(final IngredientStockDTO dto) {

        final IngredientStock stock = new IngredientStock();
        stock.setId(dto.id());
        stock.setIngredient(Ingredient.of(dto.ingredient().id()));
        stock.setNumber(dto.number());
        stock.setQuantity(dto.quantity());
        stock.setExpirationDate(dto.expirationDate());
        stock.setDeliveryDate(dto.deliveryDate());

        return stock;
    }

}
