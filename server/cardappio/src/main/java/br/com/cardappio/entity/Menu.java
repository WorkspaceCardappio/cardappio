package br.com.cardappio.entity;

import br.com.cardappio.DTO.MenuDTO;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")

public class Menu implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    @NotNull(message = Messages.EMPTY_NAME)
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    @Column
    private Boolean active = true;

    @Column
    @Length(max = 255, message = Messages.SIZE_255)
    private String note;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public static Menu of(final MenuDTO dto) {
        final Menu menu = new Menu();
        menu.setId(dto.id());
        menu.setName(dto.name());
        menu.setActive(dto.active());
        menu.setNote(dto.note());

        return menu;
    }
}
