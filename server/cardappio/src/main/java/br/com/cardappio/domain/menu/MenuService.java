package br.com.cardappio.domain.menu;

import br.com.cardappio.domain.menu.dto.MenuDTO;
import br.com.cardappio.domain.menu.adapter.MenuAdapter;
import br.com.cardappio.domain.restaurant.RestaurantRepository;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MenuService extends CrudService<Menu, UUID, MenuDTO, MenuDTO> {

    private final RestaurantRepository restaurantRepository;

    public MenuService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    protected Adapter<Menu, MenuDTO, MenuDTO> getAdapter() {
        return new MenuAdapter(restaurantRepository);
    }
}
