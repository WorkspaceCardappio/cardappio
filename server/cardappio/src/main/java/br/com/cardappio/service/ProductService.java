package br.com.cardappio.service;

import br.com.cardappio.DTO.ProductDTO;
import br.com.cardappio.adapter.ProductAdapter;
import br.com.cardappio.entity.Product;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.repository.CrudRepository;
import com.cardappio.core.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService extends CrudService<Product, ProductDTO, UUID> {


    public ProductService(final CrudRepository<Product, UUID> repository) {
        super(repository);
    }

    @Override
    protected Adapter<ProductDTO, Product> getAdapter() {
        return new ProductAdapter();
    }


}
