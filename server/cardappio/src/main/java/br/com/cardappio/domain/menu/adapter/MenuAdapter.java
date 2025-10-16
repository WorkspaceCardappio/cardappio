package br.com.cardappio.domain.menu.adapter;

import com.cardappio.core.adapter.Adapter;

import br.com.cardappio.domain.menu.Menu;
import br.com.cardappio.domain.menu.dto.MenuDTO;
import br.com.cardappio.domain.menu.dto.MenuListDTO;
import br.com.cardappio.domain.restaurant.Restaurant;

public class MenuAdapter implements Adapter<Menu, MenuListDTO, MenuDTO> {

    @Override
    public MenuListDTO toDTO(final Menu entity) {
        return new MenuListDTO(entity);
    }

    @Override
    public Menu toEntity(final MenuDTO dto) {
        return Menu.of(dto);
    }
}