package br.com.cardappio.entity;

import br.com.cardappio.DTO.TableRestaurantDTO;
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
    @Length(max=10, message = Messages.SIZE_10)
    private String number;

    @Column
    @Convert(converter = TicketStatusConverter.class)
    private TableStatus status;

    @Column
    @NotNull(message = Messages.EMPTY_PLACE)
    @Max(value = 10, message = Messages.SIZE_10)
    private Long places;

    public static TableRestaurant of(final TableRestaurantDTO dto) {
        final TableRestaurant table = new TableRestaurant();
        table.setId(dto.id());
        table.setNumber(dto.number());
        table.setStatus(dto.status());
        table.setPlaces(dto.places());

        return table;
    }
}
