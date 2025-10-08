package br.com.cardappio.domain.table;

import br.com.cardappio.domain.table.adapter.TableRestaurantAdapter;
import br.com.cardappio.domain.table.dto.TableRestaurantInsertDTO;
import br.com.cardappio.domain.table.dto.TableRestaurantListDTO;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TableRestaurantService extends CrudService<TableRestaurant, UUID, TableRestaurantListDTO, TableRestaurantInsertDTO> {

    @Override
    protected Adapter<TableRestaurant, TableRestaurantListDTO, TableRestaurantInsertDTO> getAdapter() {
        return new TableRestaurantAdapter();
    }
}
