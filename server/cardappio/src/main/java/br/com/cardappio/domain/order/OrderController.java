package br.com.cardappio.domain.order;

import br.com.cardappio.domain.order.dto.*;
import com.cardappio.core.controller.CrudController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController extends CrudController<Order, UUID, OrderListDTO, OrderDTO> {

    private final OrderService service;

    @GetMapping("/to-ticket/{id}")
    public Page<OrderToTicketDTO> findToTicket(@PathVariable final UUID id,
                                               @PageableDefault(size = 20) final Pageable pageable) {

        return service.findToTicket(id, pageable);
    }

    @GetMapping("/items/to-summary/{id}")
    public List<ProductOrderToSummaryDTO> findToSummaryDTO(@PathVariable final UUID id) {
        return service.findToSummaryDTO(id);
    }

    @PutMapping("/product/{id}/note")
    public void updateNoteInProductOrder(@PathVariable final UUID id, @RequestBody @Valid NoteDTO dto) {
        service.updateNoteInProductOrder(id, dto);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProductInOrder(@PathVariable final UUID id) {
        service.deleteProductInOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/note")
    public void updateNoteInOrder(@PathVariable final UUID id, @RequestBody @Valid NoteDTO dto) {
        service.updateNoteInOrder(id, dto);
    }

    @PostMapping("/{id}/finalize")
    public void finalize(@PathVariable final UUID id) {
        service.finalize(id);
    }

    @PostMapping("/total-by-ids")
    public List<TotalAndIdDTO> totalByIds(@RequestBody @Valid IdsDTO ids) {
        return service.getTotalByids(ids);
    }

}
