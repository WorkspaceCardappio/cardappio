package br.com.cardappio.domain.menu.adapter;

import com.cardappio.core.adapter.Adapter;

import br.com.cardappio.domain.menu.Menu;
import br.com.cardappio.domain.menu.dto.MenuDTO;
import br.com.cardappio.domain.restaurant.Restaurant;

public class MenuAdapter implements Adapter<Menu, MenuDTO, MenuDTO> {

    @Override
    public MenuDTO toDTO(final Menu entity) {
        return new MenuDTO(entity);
    }

    @Override
    public Menu toEntity(final MenuDTO dto) {

        final Menu menu = new Menu();
        menu.setId(dto.id());
        menu.setName(dto.name());
        menu.setActive(dto.active());
        menu.setNote(dto.note());
        menu.setRestaurant(Restaurant.of(dto.restaurantId()));
        menu.setTheme(dto.theme());

        return menu;
    }
}