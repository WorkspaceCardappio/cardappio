package br.com.cardappio.domain.product.item;

import br.com.cardappio.domain.product.item.adapter.ProductItemAdapter;
import br.com.cardappio.domain.product.item.dto.ProductItemDTO;
import br.com.cardappio.domain.product.item.dto.ProductItemListDTO;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductItemService extends CrudService<ProductItem, UUID, ProductItemListDTO, ProductItemDTO> {

    @Override
    protected Adapter<ProductItem, ProductItemListDTO, ProductItemDTO> getAdapter() {
        return new ProductItemAdapter();
    }
}
