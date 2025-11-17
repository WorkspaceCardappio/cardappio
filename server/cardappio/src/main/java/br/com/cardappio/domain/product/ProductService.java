package br.com.cardappio.domain.product;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import br.com.cardappio.components.s3.S3StorageComponent;
import br.com.cardappio.domain.product.adapter.ProductAdapter;
import br.com.cardappio.domain.product.dto.FlutterProductDTO;
import br.com.cardappio.domain.product.dto.ProductDTO;
import br.com.cardappio.domain.product.dto.ProductItemDTO;
import br.com.cardappio.domain.product.dto.ProductListDTO;
import br.com.cardappio.domain.product.dto.ProductToMenuDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService extends CrudService<Product, UUID, ProductListDTO, ProductDTO> {

    private final S3StorageComponent s3StorageComponent;
    private final ProductRepository repository;
    private final ProductAdapter productAdapter;

    @Override
    protected Adapter<Product, ProductListDTO, ProductDTO> getAdapter() {
        return productAdapter;
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

    public void finalize(final UUID id) {

        final Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.markAsFinalized();
        repository.save(product);

    }

    public UUID saveProduct(MultipartFile file, ProductDTO dto) {

        if (Objects.nonNull(file)) {

            String key = s3StorageComponent.getKey(file);
            dto.setImage(key);

            s3StorageComponent.saveFile(file, key, null);
        }

        return create(dto);
    }

    public void updateProduct(UUID id, MultipartFile file, ProductDTO dto) {

        if (Objects.nonNull(file)) {

            String oldImage = repository.findImageById(id);
            String newKey = s3StorageComponent.getKey(file);

            s3StorageComponent.saveFile(file, newKey, oldImage);

            dto.setImage(newKey);
        }

        update(id, dto);

    }

    @Override
    protected void beforeDelete(Product product) {

        String image = product.getImage();

        if (Objects.nonNull(image)) {

            s3StorageComponent.deleteMatchingObject(image);
        }
    }
}
