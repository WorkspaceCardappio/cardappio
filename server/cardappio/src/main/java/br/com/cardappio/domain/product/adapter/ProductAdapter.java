package br.com.cardappio.domain.product.adapter;

import br.com.cardappio.components.s3.S3StorageComponent;
import br.com.cardappio.domain.product.dto.ProductDTO;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.domain.product.dto.ProductListDTO;
import com.cardappio.core.adapter.Adapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductAdapter implements Adapter<Product, ProductListDTO, ProductDTO> {

    private final S3StorageComponent s3StorageComponent;

    @Override
    public ProductListDTO toDTO(final Product entity) {
        log.debug("Converting Product to DTO: id={}, name={}, hasImage={}",
                  entity.getId(), entity.getName(), entity.getImage() != null);

        ProductListDTO dto = new ProductListDTO(entity);
        String imageUrl = s3StorageComponent.generatePresignedUrl(entity.getImage());

        log.debug("Generated presigned URL for product {}: {}", entity.getId(), imageUrl != null ? "success" : "null");

        return new ProductListDTO(
            dto.id(),
            dto.name(),
            dto.category(),
            dto.price(),
            dto.active(),
            dto.image(),
            imageUrl
        );
    }

    @Override
    public Product toEntity(final ProductDTO dto) {
        return Product.of(dto);
    }
}
