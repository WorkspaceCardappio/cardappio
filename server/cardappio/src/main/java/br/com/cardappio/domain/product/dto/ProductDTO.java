package br.com.cardappio.domain.product.dto;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ProductDTO {

    private UUID id;

    @NotBlank(message = Messages.EMPTY_NAME)
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    private BigDecimal price;

    private BigDecimal quantity;

    private Boolean active;

    @Future
    private LocalDate expirationDate;

    @Length(max = 255, message = Messages.SIZE_255)
    private String image;

    private String imageUrl;

    @NotNull
    private UUID category;
}
