package br.com.cardappio.service;

import br.com.cardappio.DTO.MenuDTO;
import br.com.cardappio.adapter.MenuAdapter;
import br.com.cardappio.entity.Menu;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MenuService extends CrudService<Menu, UUID, MenuDTO, MenuDTO> {

    @Override
    protected Adapter<Menu, MenuDTO, MenuDTO> getAdapter() {
        return new MenuAdapter();
    }
}
