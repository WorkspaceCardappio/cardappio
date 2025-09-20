package br.com.cardappio.adapter;

import com.cardappio.core.adapter.Adapter;
import br.com.cardappio.DTO.RestaurantDTO;
import br.com.cardappio.entity.Restaurant;

public class RestaurantAdapter implements Adapter<Restaurant, RestaurantDTO, RestaurantDTO> {

    @Override
    public RestaurantDTO toDTO(Restaurant restaurant) {
        return new RestaurantDTO(restaurant);
    }

    @Override
    public Restaurant toEntity(RestaurantDTO restaurantDTO) {
        return Restaurant.of(restaurantDTO);
    }
}