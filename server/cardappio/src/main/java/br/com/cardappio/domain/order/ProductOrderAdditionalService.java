package br.com.cardappio.domain.additional;

import br.com.cardappio.domain.additional.adapter.ProductOrderAdditionalAdapter;
import br.com.cardappio.domain.additional.dto.ProductOrderAdditionalDTO;
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
