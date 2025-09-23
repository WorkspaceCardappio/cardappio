package br.com.cardappio.domain.order;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.order.adapter.ProductOrderAdapter;
import br.com.cardappio.domain.order.dto.ProductOrderDTO;

@Service
public class ProductOrderService extends CrudService<ProductOrder, UUID, ProductOrderDTO, ProductOrderDTO> {

    @Override
    protected Adapter<ProductOrder, ProductOrderDTO, ProductOrderDTO> getAdapter() {

        return new ProductOrderAdapter();
    }
}
