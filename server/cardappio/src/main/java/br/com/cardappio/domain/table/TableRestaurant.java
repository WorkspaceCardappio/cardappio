package br.com.cardappio.domain.table;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.cardappio.core.entity.EntityModel;

import br.com.cardappio.converter.TableStatusConverter;
import br.com.cardappio.domain.restaurant.Restaurant;
import br.com.cardappio.domain.table.dto.TableRestaurantDTO;
import br.com.cardappio.enums.TableStatus;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class TableRestaurant implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    @NotBlank(message = Messages.EMPTY_NUMBER)
    @Length(max = 10, message = Messages.SIZE_10)
    private String number;

    @Column
    @Convert(converter = TableStatusConverter.class)
    private TableStatus status;

    @Column
    @NotNull(message = Messages.EMPTY_PLACE)
    @Max(value = 10, message = Messages.SIZE_10)
    private Long places;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public static TableRestaurant of(final TableRestaurantDTO dto) {
        final TableRestaurant table = new TableRestaurant();
        table.setId(dto.id());
        table.setNumber(dto.number());
        table.setStatus(TableStatus.fromCode(dto.status().code()));
        table.setPlaces(dto.places());

        return table;
    }

    public static TableRestaurant of(final UUID id) {

        final TableRestaurant table = new TableRestaurant();
        table.setId(id);

        return table;
    }

}
