package br.com.cardappio.domain.order;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardappio.core.controller.CrudController;

import br.com.cardappio.domain.order.dto.NoteDTO;
import br.com.cardappio.domain.order.dto.OrderDTO;
import br.com.cardappio.domain.order.dto.OrderListDTO;
import br.com.cardappio.domain.order.dto.OrderToTicketDTO;
import br.com.cardappio.domain.order.dto.ProductOrderToSummaryDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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

    // TODO: AJUSTAR LISTAGEM

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

}
