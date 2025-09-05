package br.com.cardappio.service;


import br.com.cardappio.DTO.MenuDTO;
import br.com.cardappio.adapter.MenuAdapter;
import br.com.cardappio.entity.Menu;
import com.cardappio.core.service.CrudService;
import org.apache.coyote.Adapter;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuService extends CrudService<Menu, MenuDTO, UUID> {

    public MenuService(final CrudRepository<Menu, UUID> repository) {
        super(repository);
    }

    @Override
    protected Adapter<MenuDTO, Menu> getAdapter(){
        return new MenuAdapter();
    }
}
