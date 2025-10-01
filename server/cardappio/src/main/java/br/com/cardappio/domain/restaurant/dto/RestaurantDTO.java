package br.com.cardappio.domain.restaurant.dto;

import java.util.UUID;

import br.com.cardappio.domain.restaurant.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de detalhes do restaurante.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    private UUID id;
    private String name;
    private Boolean active;
    private String cnpj;

    public RestaurantDTO(final Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.active = restaurant.getActive();
        this.cnpj = restaurant.getCnpj();
    }
}
