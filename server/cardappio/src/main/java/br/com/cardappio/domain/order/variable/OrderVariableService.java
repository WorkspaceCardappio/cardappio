package br.com.cardappio.domain.order.variable;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.domain.order.ProductOrderRepository;
import br.com.cardappio.domain.order.ProductOrderVariable;
import br.com.cardappio.domain.order.variable.adapter.OrderVariableAdapter;
import br.com.cardappio.domain.order.variable.dto.OrderVariableDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderVariableService extends CrudService<ProductOrderVariable, UUID, OrderVariableDTO, OrderVariableDTO> {

    private final ProductOrderRepository productOrderRepository;

    @Override
    protected Adapter<ProductOrderVariable, OrderVariableDTO, OrderVariableDTO> getAdapter() {
        return new OrderVariableAdapter();
    }

    public void createItems(@Valid List<OrderVariableDTO> variables) {

        if (variables.isEmpty()) {
            return;
        }

        final UUID productOrderId = variables.getFirst().order();
        final ProductOrder productOrder = productOrderRepository.findWithVariablesById(productOrderId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        final List<ProductOrderVariable> variablesToSave = variables
                .stream()
                .map(ProductOrderVariable::of)
                .toList();

        productOrder.getVariables().clear();
        productOrder.getVariables().addAll(variablesToSave);

        productOrderRepository.save(productOrder);
    }
}