package br.com.cardappio.domain.additional;

import br.com.cardappio.domain.additional.dto.AdditionalDTO;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Additional implements EntityModel<UUID> {

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
    private String note;


    public static Additional of(final AdditionalDTO dto) {
        final Additional additional = new Additional();
        additional.setId(dto.id());
        additional.setName(dto.name());
        additional.setPrice(dto.price());
        additional.setNote(dto.note());

    return additional;
    }
}
