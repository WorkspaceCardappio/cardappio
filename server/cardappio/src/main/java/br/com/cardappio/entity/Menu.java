package br.com.cardappio.entity;

import br.com.cardappio.DTO.MenuDTO;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

import com.cardappio.core.entity.EntityModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotBlank(message = Messages.EMPTY_NAME)
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    @Column
    @NotNull
    private Boolean active = true;

    @Column
    @Length(max = 255, message = Messages.SIZE_255)
    private String note;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
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
