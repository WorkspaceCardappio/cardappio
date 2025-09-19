package br.com.cardappio.service;

import br.com.cardappio.DTO.TableRestaurantDTO;
import br.com.cardappio.adapter.TableAdapter;
import br.com.cardappio.entity.TableRestaurant;
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
