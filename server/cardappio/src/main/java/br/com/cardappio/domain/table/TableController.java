package br.com.cardappio.domain.table;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.table.dto.TableRestaurantDTO;
import br.com.cardappio.domain.table.dto.TableRestaurantToTicketDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/tables")
@RequiredArgsConstructor
public class TableController extends CrudController<TableRestaurant, UUID, TableRestaurantDTO, TableRestaurantDTO> {

    private final TableService service;

    @GetMapping("/to-ticket")
    public List<TableRestaurantToTicketDTO> findToTicket(@RequestParam(value = "search", defaultValue = "") final String search) {
        return service.findToTicket(search);
    }

}
