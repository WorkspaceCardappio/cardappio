package br.com.cardappio.domain.menu;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.menu.adapter.MenuAdapter;
import br.com.cardappio.domain.menu.dto.MenuDTO;

@Service
public class MenuService extends CrudService<Menu, UUID, MenuDTO, MenuDTO> {

    @Override
    protected Adapter<Menu, MenuDTO, MenuDTO> getAdapter() {
        return new MenuAdapter();
    }
}
