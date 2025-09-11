package br.com.cardappio.service;

import br.com.cardappio.DTO.MenuDTO;
import br.com.cardappio.adapter.MenuAdapter;
import br.com.cardappio.entity.Menu;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MenuService extends CrudService<Menu, MenuDTO, UUID> {

    @Override
    protected Adapter<MenuDTO, Menu> getAdapter() {
        return new MenuAdapter();
    }
}
