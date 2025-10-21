package br.com.cardappio.domain.category;

import br.com.cardappio.domain.category.dto.CategoryDTO;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = "id")
public class Category implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotNull(message = Messages.EMPTY_NAME)
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    @Column
    private Boolean active = true;

    @Column
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("parent")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("parent")
    private Set<Category> subCategories = new HashSet<>();

    public static Category of(final CategoryDTO dto) {
        final Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setActive(dto.getActive());
        category.setImage(dto.getImage());

        Optional.ofNullable(dto.getParent())
                .ifPresent(parent -> category.setParent(of(parent.id())));

        return category;
    }

    public static Category of(final UUID id) {
        final Category category = new Category();
        category.setId(id);

        return category;
    }
}
