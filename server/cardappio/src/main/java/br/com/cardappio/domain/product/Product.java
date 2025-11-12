package br.com.cardappio.domain.product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.cardappio.core.entity.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cardappio.domain.additional.Additional;
import br.com.cardappio.domain.category.Category;
import br.com.cardappio.domain.product.dto.ProductDTO;
import br.com.cardappio.domain.product.ingredient.ProductIngredient;
import br.com.cardappio.domain.product.item.ProductItem;
import br.com.cardappio.domain.save.SaveStatus;
import br.com.cardappio.domain.variable.ProductVariable;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Product implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotBlank(message = Messages.EMPTY_NAME)
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    @Column
    @NotNull
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal price;

    @Column
    @NotNull
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal quantity;

    @Column
    private String description;

    @Column
    private Boolean active = Boolean.TRUE;

    @Column
    @Future(message = Messages.FUTURE_DATE)
    private LocalDate expirationDate;

    @Column
    @Length(max = 255, message = Messages.SIZE_255)
    private String image;

    @Column
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name = "save_status", nullable = false, length = 10)
    private SaveStatus saveStatus = SaveStatus.DRAFT;

    @ManyToOne
    @NotNull
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Additional> additionals = new ArrayList<>();

    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductVariable> productVariables = new ArrayList<>();

    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductIngredient> productIngredients = new ArrayList<>();

    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductItem> items = new ArrayList<>();

    public static Product of(final ProductDTO dto) {

        final Product product = new Product();
        product.setId(dto.id());
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setActive(dto.active());
        product.setExpirationDate(dto.expirationDate());
        product.setImage(dto.image());
        product.setCategory(dto.category());

        return product;
    }

    public static Product of(final UUID id) {
        final Product product = new Product();
        product.setId(id);
        return product;
    }

    public void markAsFinalized() {
        saveStatus = SaveStatus.FINALIZED;
    }
}
