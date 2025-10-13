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
    @Column(name = "name", nullable = false, length = 255, unique = true)
    private String name;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @NotBlank(message = Messages.EMPTY_DOCUMENT)
    @Size(max = 14, message = Messages.SIZE_14)
    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    public static Restaurant of(final RestaurantDTO dto) {
        final Restaurant restaurant = new Restaurant();
        restaurant.setId(dto.getId());
        restaurant.setName(dto.getName());
        restaurant.setActive(dto.getActive());
        restaurant.setCnpj(dto.getCnpj());
        return restaurant;
    }

    public static Restaurant of(final UUID id) {
        final Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        return restaurant;
    }
}
