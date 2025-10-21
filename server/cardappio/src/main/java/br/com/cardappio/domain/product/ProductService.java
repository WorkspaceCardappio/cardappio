package br.com.cardappio.domain.product;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.domain.product.adapter.ProductAdapter;
import br.com.cardappio.domain.product.dto.ProductDTO;
import br.com.cardappio.domain.product.dto.ProductItemDTO;
import br.com.cardappio.domain.product.dto.ProductListDTO;
import br.com.cardappio.domain.product.dto.ProductToMenuDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService extends CrudService<Product, UUID, ProductListDTO, ProductDTO> {

    private final ProductRepository repository;

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

    public List<ProductItemDTO> findOptionsById(UUID id) {
        return repository.findOptionsById(id);
    }
}
