package br.com.cardappio.domain.product.item;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.product.item.adapter.ProductItemAdapter;
import br.com.cardappio.domain.product.item.dto.ProductItemDTO;
import br.com.cardappio.domain.product.item.dto.ProductItemListDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductItemService extends CrudService<ProductItem, UUID, ProductItemListDTO, ProductItemDTO> {

    private final ProductItemRepository repository;

    @Override
    protected Adapter<ProductItem, ProductItemListDTO, ProductItemDTO> getAdapter() {
        return new ProductItemAdapter();
    }

    public void persistItems(final List<ProductItemDTO> items) {

        final List<ProductItem> productItems = items
                .stream()
                .map(ProductItem::of)
                .toList();

        repository.saveAll(productItems);
    }
}
