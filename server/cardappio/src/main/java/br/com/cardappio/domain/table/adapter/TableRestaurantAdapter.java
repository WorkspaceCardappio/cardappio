package br.com.cardappio.domain.table.adapter;

import br.com.cardappio.domain.table.TableRestaurant;
import br.com.cardappio.domain.table.dto.TableRestaurantInsertDTO;
import br.com.cardappio.domain.table.dto.TableRestaurantListDTO;
import com.cardappio.core.adapter.Adapter;

public class TableRestaurantAdapter implements Adapter<TableRestaurant, TableRestaurantListDTO, TableRestaurantInsertDTO> {

    @Override
    public TableRestaurantListDTO toDTO(final TableRestaurant entity) {
        return new TableRestaurantListDTO(entity);
    }

    @Override
    public TableRestaurant toEntity(final TableRestaurantInsertDTO dto) {
        return TableRestaurant.of(dto);
    }
}
