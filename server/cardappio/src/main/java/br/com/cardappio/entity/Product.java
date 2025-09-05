package br.com.cardappio.entity;

import br.com.cardappio.DTO.CategoryDTO;
import br.com.cardappio.DTO.ProductDTO;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotNull(message = Messages.EMPTY_NAME)
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal quantity;

    @Column
    private boolean active;

    @Column
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private LocalDate dataVencimento;

    @Column
    private String image;

    @Column
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductIngredient> productIngredient;

    public static Product of(final ProductDTO dto) {
        final Product product = new Product();

        product.setId(dto.id());
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setActive(dto.active());
        product.setCategory(dto.category());
        product.setDataVencimento(dto.dataVencimento());
        product.setImage(dto.image());
        product.setProductIngredient(dto.productIngredient());

        return product;
    }

}
