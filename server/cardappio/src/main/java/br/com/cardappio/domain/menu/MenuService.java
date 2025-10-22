package br.com.cardappio.domain.menu;

<<<<<<< Updated upstream
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

=======
import br.com.cardappio.domain.menu.adapter.MenuAdapter;
import br.com.cardappio.domain.menu.dto.FlutterMenuDTO;
import br.com.cardappio.domain.menu.dto.MenuDTO;
>>>>>>> Stashed changes
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

<<<<<<< Updated upstream
import br.com.cardappio.domain.menu.adapter.MenuAdapter;
import br.com.cardappio.domain.menu.dto.MenuDTO;
import br.com.cardappio.domain.menu.dto.MenuListDTO;
import br.com.cardappio.domain.menu.dto.MenuProductListDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService extends CrudService<Menu, UUID, MenuListDTO, MenuDTO> {

    private final MenuRepository repository;
=======
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService extends CrudService<Menu, UUID, MenuDTO, MenuDTO> {
>>>>>>> Stashed changes

    private final MenuRepository repository;

    @Override
    protected Adapter<Menu, MenuListDTO, MenuDTO> getAdapter() {
        return new MenuAdapter();
    }

<<<<<<< Updated upstream
    public List<MenuProductListDTO> findProductsInMenu(final UUID id) {

        return repository.findByMenuId(id)
                .stream()
                .map(MenuProductListDTO::new)
                .toList();
=======
    public List<FlutterMenuDTO> getFlutterMenus() {

        return;
>>>>>>> Stashed changes
    }
}
