package br.com.cardappio.domain.restaurant;

import java.util.UUID;

import br.com.cardappio.domain.address.Address;
import br.com.cardappio.domain.restaurant.dto.RestaurantDTO;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = Messages.EMPTY_NAME)
    @Size(max = 255, message = Messages.SIZE_255)
    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Boolean active = true;

    @NotBlank(message = Messages.EMPTY_DOCUMENT)
    @Size(max = 14, message = Messages.SIZE_14)
    @Column(unique = true, nullable = false, length = 14)
    private String cnpj;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    public static Restaurant of(final RestaurantDTO dto) {
        final Restaurant restaurant = new Restaurant();
        restaurant.setId(dto.id());
        restaurant.setName(dto.name());
        restaurant.setActive(dto.active());
        restaurant.setCnpj(dto.cnpj());
        return restaurant;
    }
}
