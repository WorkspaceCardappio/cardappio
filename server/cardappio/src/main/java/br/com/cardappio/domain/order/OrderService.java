package br.com.cardappio.domain.order;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.order.adapter.OrderAdapter;
import br.com.cardappio.domain.order.dto.OrderDTO;
import br.com.cardappio.domain.order.dto.OrderToTicketDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService extends CrudService<Order, UUID, OrderDTO, OrderDTO> {

    private final OrderRepository repository;

    @Override
    protected Adapter<Order, OrderDTO, OrderDTO> getAdapter() {
        return new OrderAdapter();
    }

    public Page<OrderToTicketDTO> findToTicket(final UUID id, final Pageable pageable) {
        return repository.findByTicketId(id, pageable)
                .map(OrderToTicketDTO::new);
    }

    @Override
    @Transactional
    public void update(UUID id, OrderDTO orderDTO) {
        Order existingOrder = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com o ID: " + id));

        if (orderDTO.ticketId() == null) {
            throw new IllegalArgumentException("O ID da comanda (ticket) é obrigatório para a atualização.");
        }

        existingOrder.setTotal(orderDTO.total());
        setTicket(existingOrder, orderDTO.ticketId());

        existingOrder.getProductOrders().clear();
        addProductOrders(existingOrder, orderDTO.products());

        repository.save(existingOrder);
    }

    private void setTicket(Order order, UUID ticketId) {
        Ticket ticket = ticketRepository.getReferenceById(ticketId);

        if (!TicketStatus.OPEN.equals(ticket.getStatus())) {
            throw new IllegalArgumentException("A comanda não está aberta.");
        }
        order.setTicket(ticket);
    }

    private void addProductOrders(Order order, Iterable<ProductOrderDTO> productDTOs) {
        for (ProductOrderDTO productDTO : productDTOs) {
            ProductOrder productOrder = createProductOrder(productDTO, order);
            order.getProductOrders().add(productOrder);
        }
    }

    private ProductOrder createProductOrder(ProductOrderDTO productDTO, Order order) {
        ProductOrder productOrder = new ProductOrder();
        Product product = productRepository.getReferenceById(productDTO.productId());

        productOrder.setProduct(product);
        productOrder.setQuantity(productDTO.quantity());
        productOrder.setOrder(order);

            productOrder.setPrice(product.getPrice());
            productOrder.setTotal(product.getPrice().multiply(productDTO.quantity()));

        return productOrder;
    }
}