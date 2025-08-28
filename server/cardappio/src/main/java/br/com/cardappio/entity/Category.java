package br.com.cardappio.entity;

import br.com.cardappio.DTO.CategoryDTO;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Category implements EntityModel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    @Length(max = 255)
    private String name;

    @Column
    @NotNull
    private Boolean active = true;

    @Column
    @Length(max = 255)
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category subCategory;

    public static Category of(final CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.id());
        category.setName(dto.name());
        category.setActive(dto.active());
        category.setImage(dto.image());
        category.setSubCategory(dto.subCategory());

        return category;
    }
}
