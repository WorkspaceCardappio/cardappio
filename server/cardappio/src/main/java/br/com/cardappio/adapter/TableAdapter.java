package br.com.cardappio.adapter;

import br.com.cardappio.DTO.TableRestaurantDTO;
import br.com.cardappio.entity.TableRestaurant;
import com.cardappio.core.adapter.Adapter;

public class TableAdapter implements Adapter<TableRestaurant, TableRestaurantDTO, TableRestaurantDTO> {

    @Override
    public TableRestaurantDTO toDTO(final TableRestaurant entity) {
        return new TableRestaurantDTO(entity);
    }

    @Override
    public TableRestaurant toEntity(final TableRestaurantDTO dto) {
        return TableRestaurant.of(dto);
    }
}
