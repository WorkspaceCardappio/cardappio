package br.com.cardappio.domain.order;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.order.adapter.OrderAdapter;
import br.com.cardappio.domain.order.dto.IdsDTO;
import br.com.cardappio.domain.order.dto.NoteDTO;
import br.com.cardappio.domain.order.dto.OrderDTO;
import br.com.cardappio.domain.order.dto.OrderListDTO;
import br.com.cardappio.domain.order.dto.OrderToTicketDTO;
import br.com.cardappio.domain.order.dto.ProductOrderToSummaryDTO;
import br.com.cardappio.domain.order.dto.TotalAndIdDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService extends CrudService<Order, UUID, OrderListDTO, OrderDTO> {

    private static final String ORDER_NOT_FOUND = "Pedido não encontrado";
    private final OrderRepository repository;
    private final ProductOrderRepository productOrderRepository;

    @Override
    protected Adapter<Order, OrderListDTO, OrderDTO> getAdapter() {
        return new OrderAdapter();
    }

    public Page<OrderToTicketDTO> findToTicket(final UUID id, final Pageable pageable) {
        return repository.findByTicketId(id, pageable)
                .map(OrderToTicketDTO::new);
    }

    public List<ProductOrderToSummaryDTO> findToSummaryDTO(final UUID id) {
        return productOrderRepository.findSummaryByOrderId(id);
    }

    public void updateNoteInProductOrder(final UUID id, final NoteDTO dto) {

        final ProductOrder productOrder = productOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));

        productOrder.setNote(dto.note());
        productOrderRepository.save(productOrder);
    }

    public void updateNoteInOrder(final UUID id, final NoteDTO dto) {

        final Order order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));

        order.setNote(dto.note());
        repository.save(order);
    }

    public void deleteProductInOrder(final UUID id) {

        final ProductOrder productOrder = productOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));

        productOrderRepository.delete(productOrder);
    }

    public void finalize(final UUID id) {

        final Order order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));

        order.markAsFinalized();
        repository.save(order);

    }

    public List<TotalAndIdDTO> getTotalByids(final IdsDTO body) {
        return repository.findTotalByIds(body.ids());
    }
}