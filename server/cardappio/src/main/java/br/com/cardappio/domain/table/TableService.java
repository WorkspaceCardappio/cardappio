package br.com.cardappio.domain.table;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.table.adapter.TableAdapter;
import br.com.cardappio.domain.table.dto.TableRestaurantDTO;
import br.com.cardappio.domain.table.dto.TableRestaurantToTicketDTO;

@Service
public class TableService extends CrudService<TableRestaurant, UUID, TableRestaurantDTO, TableRestaurantDTO> {

    @Override
    protected Adapter<TableRestaurant, TableRestaurantDTO, TableRestaurantDTO> getAdapter() {
        return new TableAdapter();
    }

    public List<TableRestaurantToTicketDTO> findToTicket(final String search) {

        return this.findAllRSQL(search, Pageable.ofSize(100))
                .map(table -> new TableRestaurantToTicketDTO(table.getId(), table.getNumber()))
                .stream()
                .toList();
    }
}
