package br.com.cardappio.domain.product.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.cardappio.domain.category.Category;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInsertDTO {

    @NotBlank(message = Messages.EMPTY_NAME)
    @Length(max = 255, message = Messages.SIZE_255)
    private String name;

    @NotNull
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal price;

    @NotNull
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal quantity;

    private Boolean active = true;

    @Future
    private LocalDate expirationDate;

    @Length(max = 255, message = Messages.SIZE_255)
    private String image;

    @NotNull
    private Category category;
}
