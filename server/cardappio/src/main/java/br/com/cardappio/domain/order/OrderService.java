package br.com.cardappio.domain.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.ingredient.Ingredient;
import br.com.cardappio.domain.ingredient.IngredientRepository;
import br.com.cardappio.domain.ingredient.IngredientStock;
import br.com.cardappio.domain.order.adapter.OrderAdapter;
import br.com.cardappio.domain.order.dto.ChangeStatusDTO;
import br.com.cardappio.domain.order.dto.FlutterCreateOrderDTO;
import br.com.cardappio.domain.order.dto.IdsDTO;
import br.com.cardappio.domain.order.dto.NoteDTO;
import br.com.cardappio.domain.order.dto.OrderDTO;
import br.com.cardappio.domain.order.dto.OrderListDTO;
import br.com.cardappio.domain.order.dto.OrderToTicketDTO;
import br.com.cardappio.domain.order.dto.ProductOrderToSummaryDTO;
import br.com.cardappio.domain.order.dto.TotalAndIdDTO;
import br.com.cardappio.domain.product.item.ProductItemIngredient;
import br.com.cardappio.domain.save.SaveStatus;
import br.com.cardappio.domain.stock.IngredientStockRepository;
import br.com.cardappio.enums.OrderStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService extends CrudService<Order, UUID, OrderListDTO, OrderDTO> {

    private static final String ORDER_NOT_FOUND = "Pedido não encontrado";

    private final OrderRepository repository;
    private final ProductOrderRepository productOrderRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientStockRepository ingredientStockRepository;

    @Override
    protected Adapter<Order, OrderListDTO, OrderDTO> getAdapter() {
        return new OrderAdapter();
    }

    public Page<OrderToTicketDTO> findToTicket(final UUID id, final Pageable pageable) {
        return repository.findByTicketIdAndSaveStatus(id, SaveStatus.FINALIZED, pageable)
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

    @Transactional
    public void finalize(final UUID id) {
        final Order order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));

        List<Ingredient> changedAggregates = new ArrayList<>();
        List<ProductOrder> productOrders = productOrderRepository.findAllWithIngredientsByOrderId(id);

        for (ProductOrder productOrder : productOrders) {
            if (productOrder.getProductItem() == null) {
                continue;
            }

            if (productOrder.getProductItem().getIngredients().isEmpty()) {
                continue;
            }

            productOrder.getProductItem().getIngredients()
                    .forEach(pi -> debitFromLots(pi, productOrder.getQuantity(), changedAggregates));
        }

        if (!changedAggregates.isEmpty()) {
            ingredientRepository.saveAll(changedAggregates);
        }

        order.markAsFinalized();
        repository.save(order);
    }

    private void debitFromLots(final ProductItemIngredient piIngredient,
            final BigDecimal orderQuantity,
            final List<Ingredient> changedAggregates) {

        if (piIngredient == null ||
                piIngredient.getIngredient() == null ||
                piIngredient.getQuantity() == null ||
                orderQuantity == null) {
            return;
        }

        final Ingredient ingredient = piIngredient.getIngredient();
        BigDecimal totalToConsume = piIngredient.getQuantity().multiply(orderQuantity);

        if (totalToConsume.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        List<IngredientStock> lots =
                ingredientStockRepository.findAvailableByIngredient(ingredient.getId());

        for (IngredientStock lot : lots) {
            if (totalToConsume.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            BigDecimal lotQty = lot.getQuantity();
            if (lotQty == null || lotQty.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            if (lotQty.compareTo(totalToConsume) > 0) {
                lot.setQuantity(lotQty.subtract(totalToConsume));
                totalToConsume = BigDecimal.ZERO;
            } else {
                totalToConsume = totalToConsume.subtract(lotQty);
                lot.setQuantity(BigDecimal.ZERO);
            }
        }

        if (!lots.isEmpty()) {
            ingredientStockRepository.saveAll(lots);
        }

        BigDecimal newAggregate =
                ingredientStockRepository.sumQuantityByIngredient(ingredient.getId());
        ingredient.setQuantity(newAggregate);
        changedAggregates.add(ingredient);
    }

    public List<TotalAndIdDTO> getTotalByids(final IdsDTO body) {

        if (body.ids().isEmpty()) {
            return List.of();
        }

        return repository.findTotalByIds(body.ids());
    }

    public void createFlutterOrder(FlutterCreateOrderDTO dto) {
        UUID idSavedOrder = repository.save(Order.of(dto)).getId();
        List<ProductOrder> productOrders = new ArrayList<>();

        dto.items().forEach(item -> {
            ProductOrder productOrder = ProductOrder.of(item);
            productOrder.setOrder(Order.of(idSavedOrder));
            productOrder.setPrice(BigDecimal.valueOf(item.lineTotal()));

            if (item.variablesId() != null && !item.variablesId().isEmpty()) {
                for (UUID varId : item.variablesId()) {
                    ProductOrderVariable variableEntity = ProductOrderVariable.ofFlutter(varId, productOrder);
                    productOrder.getVariables().add(variableEntity);
                }
            }

            productOrders.add(productOrder);
        });

        productOrderRepository.saveAll(productOrders);
    }

    public void changeStatus(final UUID id, final ChangeStatusDTO statusDTO) {

        final OrderStatus status = OrderStatus.fromCode(statusDTO.code());
        final Order order = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ORDER_NOT_FOUND));
        order.setStatus(status);
        repository.save(order);
    }
}


