package br.com.cardappio.adapter;

import br.com.cardappio.DTO.MenuDTO;
import br.com.cardappio.entity.Menu;
import com.cardappio.core.adapter.Adapter;

public class MenuAdapter implements Adapter<MenuDTO, Menu> {

    @Override
    public MenuDTO toDTO(Menu entity) {
        return new MenuDTO(entity);
    }

    @Override
    public Menu toEntity(MenuDTO dto) {
        return Menu.of(dto);
    }
}