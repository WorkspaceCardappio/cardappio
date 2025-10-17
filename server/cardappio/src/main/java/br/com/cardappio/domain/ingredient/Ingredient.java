package br.com.cardappio.domain.ingredient;

import br.com.cardappio.converter.UnityOfMeasurementConverter;
import br.com.cardappio.domain.ingredient.dto.IngredientDTO;
import br.com.cardappio.domain.ingredient.dto.IngredientStockDTO;
import br.com.cardappio.enums.UnityOfMeasurement;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    @Column(name="unity_of_measurement", nullable = false)
    @Convert(converter = UnityOfMeasurementConverter.class)
    private UnityOfMeasurement unityOfMeasurement;

    @Column(nullable = false)
    @NotNull
    private Boolean active = Boolean.TRUE;

    @Column(nullable = false)
    @NotNull
    private Boolean allergenic = Boolean.FALSE;

    @JsonIgnoreProperties(value = "ingredient")
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientStock> stocks = new ArrayList<>();

    public static Ingredient of(final IngredientDTO dto) {

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(dto.id());
        ingredient.setName(dto.name());
        ingredient.setUnityOfMeasurement(UnityOfMeasurement.fromCode(dto.unityOfMeasurement().code()));
        ingredient.setActive(dto.active());
        ingredient.setAllergenic(dto.allergenic());

        final BigDecimal quantity = dto.stocks()
                .stream()
                .map(IngredientStockDTO::quantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final List<IngredientStock> stocks = dto.stocks()
                .stream()
                .map(stock -> IngredientStock.of(stock, ingredient))
                .toList();

        ingredient.setQuantity(quantity);
        ingredient.setStocks(stocks);
        return ingredient;
    }

    public static Ingredient of(final UUID id) {

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(id);

        return ingredient;
    }


}
