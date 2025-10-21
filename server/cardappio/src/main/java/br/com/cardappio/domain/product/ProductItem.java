package br.com.cardappio.domain.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cardappio.core.entity.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cardappio.converter.ItemTypeConverter;
import br.com.cardappio.enums.ItemSize;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@ToString(of = {"id", "quantity", "size"})
@EqualsAndHashCode(of = "id")
public class ProductItem implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @NotNull(message = Messages.PRODUCT_NOT_NULL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Column(nullable = false)
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal quantity;

    @Column(name = "size_item")
    @Convert(converter = ItemTypeConverter.class)
    private ItemSize size = ItemSize.UNIQUE;

    @Column
    @Size(max = 255)
    private String description;

    @Column(nullable = false)
    @NotNull(message = Messages.EMPTY_PRICE)
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean active = true;

    @JsonIgnoreProperties("item")
    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductItemIngredient> ingredients = new ArrayList<>();

}
