package br.com.cardappio.domain.table;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.table.adapter.TableRestaurantAdapter;
import br.com.cardappio.domain.table.dto.TableRestaurantInsertDTO;
import br.com.cardappio.domain.table.dto.TableRestaurantListDTO;
import br.com.cardappio.domain.table.dto.TableRestaurantToTicketDTO;

@Service
public class TableRestaurantService extends CrudService<TableRestaurant, UUID, TableRestaurantListDTO, TableRestaurantInsertDTO> {

    @Override
    protected Adapter<TableRestaurant, TableRestaurantListDTO, TableRestaurantInsertDTO> getAdapter() {
        return new TableRestaurantAdapter();
    }

    public List<TableRestaurantToTicketDTO> findToTicket(final String search) {

        return this.findAllRSQL(search, Pageable.ofSize(100))
                .map(table -> new TableRestaurantToTicketDTO(table.getId(), table.getNumber()))
                .stream()
                .toList();
    }
}
