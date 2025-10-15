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
import jakarta.persistence.EntityNotFoundException;
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

        setTicket(newOrder, orderDTO.ticketId());
        addProductOrders(newOrder, orderDTO.products());
        newOrder.setTotal(orderDTO.total());

        return repository.save(newOrder).getId();
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

        if (product.getPrice() != null) {
            productOrder.setPrice(product.getPrice());
            productOrder.setTotal(product.getPrice().multiply(productDTO.quantity()));
        }

        return productOrder;
    }
}