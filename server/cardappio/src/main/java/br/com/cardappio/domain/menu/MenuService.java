package br.com.cardappio.domain.menu;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.menu.adapter.MenuAdapter;
import br.com.cardappio.domain.menu.dto.MenuDTO;
import br.com.cardappio.domain.menu.dto.MenuListDTO;
import br.com.cardappio.domain.menu.dto.MenuProductListDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService extends CrudService<Menu, UUID, MenuListDTO, MenuDTO> {

    private final MenuRepository repository;

    @Override
    protected Adapter<Menu, MenuListDTO, MenuDTO> getAdapter() {
        return new MenuAdapter();
    }

    public List<MenuProductListDTO> findProductsInMenu(final UUID id) {

        return repository.findByMenuId(id)
                .stream()
                .map(MenuProductListDTO::new)
                .toList();
    }
}
