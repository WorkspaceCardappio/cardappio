package br.com.cardappio.domain.order;

import br.com.cardappio.domain.order.adapter.OrderAdapter;
import br.com.cardappio.domain.order.dto.*;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Transactional
    public void createFlutterOrder(FlutterCreateOrderDTO dto) {

        UUID idSavedOrder = repository.save(Order.of(dto)).getId();

        List<ProductOrder> productOrders = new ArrayList<>();

        dto.items().forEach(item -> {

            ProductOrder productOrder = ProductOrder.of(item);
            productOrder.setOrder(Order.of(idSavedOrder));
            productOrder.getVariables().add(ProductOrderVariable.of(item.variableId()));

            productOrders.add(productOrder);
//            productOrder.getAdditionals().addAll(item.additionals()
//                    .stream()
//                    .map(ProductOrderAdditional::of)
//                    .toList());
        });

        productOrderRepository.saveAll(productOrders);


    }
}