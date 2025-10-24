package br.com.cardappio.domain.product;

import br.com.cardappio.domain.product.adapter.ProductAdapter;
import br.com.cardappio.domain.product.dto.*;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public List<FlutterProductDTO> findFlutterProducts(UUID idCategory) {

        return repository.findFlutterProducts(idCategory);
    }
}
