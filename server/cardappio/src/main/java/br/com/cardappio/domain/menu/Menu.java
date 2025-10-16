package br.com.cardappio.domain.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.cardappio.core.entity.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cardappio.domain.menu.dto.MenuDTO;
import br.com.cardappio.domain.restaurant.Restaurant;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
public class Menu implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotBlank(message = Messages.EMPTY_NAME)
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    @Column
    @NotNull
    private Boolean active = true;

    @Column
    @Length(max = 255, message = Messages.SIZE_255)
    private String note;

    @Column
    @Length(max = 30, message = Messages.SIZE_30)
    private String theme;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @JsonIgnoreProperties(value = "menu")
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuProduct> products = new ArrayList<>();

    public static Menu of(final MenuDTO dto) {

        final Menu menu = new Menu();
        menu.setId(dto.id());
        menu.setName(dto.name());
        menu.setActive(dto.active());
        menu.setNote(dto.note());
        menu.setTheme(dto.theme());

        final List<MenuProduct> products = dto.products()
                .stream()
                .map(menuProduct -> MenuProduct.of(menuProduct, menu))
                .toList();

        menu.setProducts(products);
        return menu;
    }

}
