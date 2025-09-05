package br.com.cardappio.adapter;

import br.com.cardappio.DTO.MenuDTO;
import br.com.cardappio.entity.Menu;


public class MenuAdapter implements Addpter<MenuDTO, Menu> {

    @Override
    public MenuDTO toDto(final Menu entity) {
        return new MenuDTO(entity);
    }

    @Override
    public Menu toEntity(final MenuDTO dto) {
        return Menu.of(dto);
    }
}
