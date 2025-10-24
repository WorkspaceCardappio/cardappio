package br.com.cardappio.domain.table;

import br.com.cardappio.domain.table.dto.TableRestaurantInsertDTO;
import br.com.cardappio.domain.table.dto.TableRestaurantListDTO;
import br.com.cardappio.domain.table.dto.TableRestaurantToTicketDTO;
import com.cardappio.core.controller.CrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tables-restaurant")
@RequiredArgsConstructor
public class TableRestaurantController extends CrudController<TableRestaurant, UUID, TableRestaurantListDTO, TableRestaurantInsertDTO> {

    private final TableRestaurantService service;

    @GetMapping("/to-ticket")
    public List<TableRestaurantToTicketDTO> findToTicket(@RequestParam(value = "search", defaultValue = "") final String search) {
        return service.findToTicket(search);
    }

}
