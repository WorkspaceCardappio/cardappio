package br.com.cardappio.entity;

import br.com.cardappio.DTO.RestaurantDTO;
import com.cardappio.core.entity.EntityModel;
import br.com.cardappio.utils.Messages;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Restaurant implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    @NotBlank(message = Messages.EMPTY_NAME)
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "address_id")
    private Address address;

    @Column
    @Length(max = 14, message = Messages.SIZE_14)
    private String cnpj;

    public static Restaurant of(final RestaurantDTO dto){

        final Restaurant restaurant = new Restaurant();
        restaurant.setId(dto.id());
        restaurant.setName(dto.name());
        restaurant.setActive(dto.active());
        restaurant.setCnpj(dto.cnpj());

        return restaurant;
    }
}