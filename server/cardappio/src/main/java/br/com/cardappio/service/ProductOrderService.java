package br.com.cardappio.service;

import br.com.cardappio.DTO.ProductOrderDTO;
import br.com.cardappio.adapter.ProductOrderAdapter;
import br.com.cardappio.entity.ProductOrder;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductOrderService extends CrudService<ProductOrder, ProductOrderDTO, UUID> {

    @Override
    protected Adapter<ProductOrderDTO, ProductOrder> getAdapter() {

        return new ProductOrderAdapter();
    }
}
