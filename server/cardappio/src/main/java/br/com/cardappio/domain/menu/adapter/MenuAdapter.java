package br.com.cardappio.domain.menu.adapter;



import br.com.cardappio.domain.menu.dto.MenuDTO;
import br.com.cardappio.domain.menu.Menu;
import br.com.cardappio.domain.restaurant.Restaurant;
import br.com.cardappio.domain.restaurant.RestaurantRepository;
import lombok.AllArgsConstructor;

import com.cardappio.core.adapter.Adapter;

@AllArgsConstructor
public class MenuAdapter implements Adapter<Menu, MenuDTO, MenuDTO> {

    private RestaurantRepository restaurantRepository;

    @Override
    public MenuDTO toDTO(final Menu entity) {
        return new MenuDTO(entity);
    }

    @Override
    public Menu toEntity(final MenuDTO dto) {

        final Restaurant restaurant = restaurantRepository.getReferenceById(dto.restaurantId());

        final Menu menu = new Menu();
        menu.setId(dto.id());
        menu.setName(dto.name());
        menu.setActive(dto.active());
        menu.setNote(dto.note());
        menu.setRestaurant(restaurant);
        menu.setTheme(dto.theme());


        return menu;    }
}