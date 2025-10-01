package br.com.cardappio.domain.restaurant.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantListDTO {
    private UUID id;
    private String name;
    private Boolean active;
}
