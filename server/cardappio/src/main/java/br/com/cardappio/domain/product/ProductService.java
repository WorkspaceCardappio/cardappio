package br.com.cardappio.domain.product;

import br.com.cardappio.domain.product.dto.ProductDTO;
import br.com.cardappio.domain.product.adapter.ProductAdapter;
import br.com.cardappio.domain.product.dto.ProductListDTO;
import br.com.cardappio.domain.product.dto.ProductToMenuDTO;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService extends CrudService<Product, UUID, ProductListDTO, ProductDTO> {

    @Override
    protected Adapter<Product, ProductListDTO, ProductDTO> getAdapter() {
        return new ProductAdapter();
    }

    public List<ProductToMenuDTO> findToMenu(final String search) {

        return this.findAllRSQL(search, Pageable.ofSize(100))
                .map(ProductToMenuDTO::new)
                .stream()
                .toList();
    }
}
