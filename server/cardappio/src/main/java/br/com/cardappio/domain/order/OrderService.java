package br.com.cardappio.domain.order;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.order.adapter.OrderAdapter;
import br.com.cardappio.domain.order.dto.OrderDTO;
import br.com.cardappio.domain.order.dto.ProductOrderDTO;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.domain.product.ProductRepository;
import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.domain.ticket.TicketRepository;
import br.com.cardappio.enums.OrderStatus;
import jakarta.transaction.Transactional;

@Service
public class OrderService extends CrudService<Order, UUID, OrderDTO, OrderDTO> {

    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final TicketRepository ticketRepository;

    public OrderService(OrderRepository repository, ProductRepository productRepository, TicketRepository ticketRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.ticketRepository = ticketRepository;

        super.setRepository(repository);
    }

    @Override
    protected Adapter<Order, OrderDTO, OrderDTO> getAdapter() {
        return new OrderAdapter();
    }

    @Override
    @Transactional
    public UUID create(OrderDTO orderDTO) {

        Order newOrder = new Order();
        newOrder.setStatus(OrderStatus.PENDING);

        Ticket ticketReference = ticketRepository.getReferenceById(orderDTO.ticketId());
        newOrder.setTicket(ticketReference);

        for (ProductOrderDTO productDTO : orderDTO.products()) {
            ProductOrder productOrder = new ProductOrder();

            Product productReference = productRepository.getReferenceById(productDTO.productId());
            productOrder.setProduct(productReference);
            productOrder.setQuantity(productDTO.quantity());

            if (productReference.getPrice() != null) {
                productOrder.setPrice(productReference.getPrice());
                productOrder.setTotal(productReference.getPrice().multiply(productDTO.quantity()));
            }

            productOrder.setOrder(newOrder);
            newOrder.getProductOrders().add(productOrder);
        }

        newOrder.setTotal(orderDTO.total());

        Order savedOrder = repository.save(newOrder);

        return savedOrder.getId();
    }
}

