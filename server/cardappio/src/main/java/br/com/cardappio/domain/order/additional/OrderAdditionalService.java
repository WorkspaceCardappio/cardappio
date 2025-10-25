package br.com.cardappio.domain.order.additional;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.domain.order.ProductOrderAdditional;
import br.com.cardappio.domain.order.ProductOrderRepository;
import br.com.cardappio.domain.order.additional.adapter.OrderAdditionalAdapter;
import br.com.cardappio.domain.order.additional.dto.OrderAdditionalDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderAdditionalService extends CrudService<ProductOrderAdditional, UUID, OrderAdditionalDTO, OrderAdditionalDTO> {

    private final ProductOrderRepository productOrderRepository;

    @Override
    protected Adapter<ProductOrderAdditional, OrderAdditionalDTO, OrderAdditionalDTO> getAdapter() {
        return new OrderAdditionalAdapter();
    }

    public void createItems(@Valid List<OrderAdditionalDTO> additionals) {

        if (additionals.isEmpty()) {
            return;
        }

        final UUID productOrderId = additionals.getFirst().order();
        final ProductOrder productOrder = productOrderRepository.findWithAdditionalsById(productOrderId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        final List<ProductOrderAdditional> additionalsToSave = additionals
                .stream()
                .map(ProductOrderAdditional::of)
                .toList();

        productOrder.getAdditionals().clear();
        productOrder.getAdditionals().addAll(additionalsToSave);

        productOrderRepository.save(productOrder);
    }
}