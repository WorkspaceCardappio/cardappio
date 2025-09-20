package br.com.cardappio.adapter;

import org.springframework.stereotype.Component;

import br.com.cardappio.DTO.MenuDTO;
import br.com.cardappio.entity.Menu;
import com.cardappio.core.adapter.Adapter;

@Component
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