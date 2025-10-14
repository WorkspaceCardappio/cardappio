package br.com.cardappio.domain.order;

import br.com.cardappio.domain.order.adapter.ProductOrderAdditionalAdapter;
import br.com.cardappio.domain.order.dto.ProductOrderAdditionalDTO;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductOrderAdditionalService extends CrudService<ProductOrderAdditional, UUID, ProductOrderAdditionalDTO, ProductOrderAdditionalDTO> {
    @Override
    protected Adapter<ProductOrderAdditional, ProductOrderAdditionalDTO, ProductOrderAdditionalDTO> getAdapter() {
        return new ProductOrderAdditionalAdapter();
    }
}
