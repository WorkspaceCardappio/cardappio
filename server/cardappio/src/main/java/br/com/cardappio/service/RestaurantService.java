package br.com.cardappio.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.DTO.RestaurantDTO;
import br.com.cardappio.adapter.RestaurantAdapter;
import br.com.cardappio.entity.Restaurant;

@Service
public class RestaurantService extends CrudService<Restaurant, UUID, RestaurantDTO, RestaurantDTO> {

    @Override
    protected Adapter<Restaurant, RestaurantDTO, RestaurantDTO> getAdapter() {
        return new RestaurantAdapter();
    }
}
