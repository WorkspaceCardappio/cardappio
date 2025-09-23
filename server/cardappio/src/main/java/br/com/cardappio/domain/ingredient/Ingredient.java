package br.com.cardappio.domain.ingredient;

import br.com.cardappio.domain.ingredient.dto.IngredientDTO;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class Ingredient implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotBlank
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    @Column(nullable = false)
    @NotNull
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal quantity = BigDecimal.ZERO;

    @Column(name = "expiration_date", nullable = false)
    @NotNull
    private LocalDate expirationDate;

    @Column(nullable = false)
    @NotNull
    private Boolean allergenic = Boolean.FALSE;

    public static Ingredient of(final IngredientDTO dto) {

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(dto.id());
        ingredient.setName(dto.name());
        ingredient.setQuantity(dto.quantity());
        ingredient.setExpirationDate(dto.expirationDate());
        ingredient.setAllergenic(dto.allergenic());

        return ingredient;
    }

}
