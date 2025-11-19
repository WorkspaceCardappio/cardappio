package br.com.cardappio.domain.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.ticket.dto.TicketListDTO;
import br.com.cardappio.utils.EnumDTO;

public record OrderKitchenDTO(

        UUID id,
        Long number,
        EnumDTO status,
        TicketListDTO ticket,
        LocalDateTime createdAt,
        String observation,
        List<ProductOrderItemDTO> items
) {

    public OrderKitchenDTO(final Order order) {
        this(
                order.getId(),
                order.getNumber(),
                order.getStatus().toDTO(),
                new TicketListDTO(order.getTicket()),
                order.getCreatedAt(),
                order.getNote(),
                order.getProductOrders().stream()
                        .map(ProductOrderItemDTO::new)
                        .collect(Collectors.toList())
        );
    }

    public record ProductOrderItemDTO(
            UUID id,
            ProductDTO product,
            Integer quantity,
            String observation
    ) {
        public ProductOrderItemDTO(final br.com.cardappio.domain.order.ProductOrder productOrder) {
            this(
                    productOrder.getId(),
                    new ProductDTO(
                            productOrder.getProductItem().getProduct().getId(),
                            productOrder.getProductItem().getProduct().getName()
                    ),
                    productOrder.getQuantity().intValue(),
                    productOrder.getNote()
            );
        }
    }

    public record ProductDTO(
            UUID id,
            String name
    ) {
    }

}
