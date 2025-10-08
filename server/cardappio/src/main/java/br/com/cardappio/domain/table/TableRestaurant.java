package br.com.cardappio.domain.table;

import br.com.cardappio.converter.TableStatusConverter;
import br.com.cardappio.domain.restaurant.Restaurant;
import br.com.cardappio.domain.table.dto.TableRestaurantDTO;
import br.com.cardappio.converter.TicketStatusConverter;
import br.com.cardappio.enums.TableStatus;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

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
        table.setStatus(dto.status());
        table.setPlaces(dto.places());

        return table;
    }

    public static TableRestaurant of(final UUID id) {

        final TableRestaurant table = new TableRestaurant();
        table.setId(id);

        return table;
    }

}
