package br.com.cardappio.domain.menu.adapter;

import br.com.cardappio.domain.menu.dto.MenuDTO;
import br.com.cardappio.domain.menu.Menu;
import com.cardappio.core.adapter.Adapter;

public class MenuAdapter implements Adapter<Menu, MenuDTO, MenuDTO> {

    @Override
    public MenuDTO toDTO(final Menu entity) {
        return new MenuDTO(entity);
    }

    @Override
    public Menu toEntity(final MenuDTO dto) {
        return Menu.of(dto);
    }
}