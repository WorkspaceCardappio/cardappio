package br.com.cardappio.adapter;

import br.com.cardappio.DTO.MenuDTO;
import br.com.cardappio.entity.Menu;

public interface Addpter<T, T1> {
    MenuDTO toDto(Menu entity);

    Menu toEntity(MenuDTO dto);
}
