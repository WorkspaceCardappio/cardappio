package br.com.cardappio.domain.order.item;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.order.OrderRepository;
import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.domain.order.item.adapter.OrderItemAdapter;
import br.com.cardappio.domain.order.item.dto.OrderItemDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService extends CrudService<ProductOrder, UUID, OrderItemDTO, OrderItemDTO> {

    private final OrderRepository repository;

    @Override
    protected Adapter<ProductOrder, OrderItemDTO, OrderItemDTO> getAdapter() {
        return new OrderItemAdapter();
    }
}