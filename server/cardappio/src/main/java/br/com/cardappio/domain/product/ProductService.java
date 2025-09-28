package br.com.cardappio.domain.product;

import br.com.cardappio.domain.product.dto.ProductDTO;
import br.com.cardappio.domain.product.adapter.ProductAdapter;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService extends CrudService<Product, UUID, ProductDTO, ProductDTO> {


    @Override
    protected Adapter<Product, ProductDTO, ProductDTO> getAdapter() {
        return new ProductAdapter();
    }
}
