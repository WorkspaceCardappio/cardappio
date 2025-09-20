package br.com.cardappio.domain.table;

import br.com.cardappio.domain.table.dto.TableRestaurantDTO;
import br.com.cardappio.domain.table.adapter.TableAdapter;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TableService extends CrudService<TableRestaurant, UUID, TableRestaurantDTO, TableRestaurantDTO> {

    @Override
    protected Adapter<TableRestaurant, TableRestaurantDTO, TableRestaurantDTO> getAdapter() {
        return new TableAdapter();
    }
}
