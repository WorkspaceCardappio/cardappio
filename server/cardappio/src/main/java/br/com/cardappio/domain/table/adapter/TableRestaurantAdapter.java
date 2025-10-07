package br.com.cardappio.domain.table.adapter;

import br.com.cardappio.domain.table.TableRestaurant;
import br.com.cardappio.domain.table.dto.TableRestaurantDTO;
import com.cardappio.core.adapter.Adapter;

public class TableRestaurantAdapter implements Adapter<TableRestaurant, TableRestaurantDTO, TableRestaurantDTO> {

    @Override
    public TableRestaurantDTO toDTO(final TableRestaurant entity) {
        return new TableRestaurantDTO(entity);
    }

    @Override
    public TableRestaurant toEntity(final TableRestaurantDTO dto) {
        return TableRestaurant.of(dto);
    }
}
