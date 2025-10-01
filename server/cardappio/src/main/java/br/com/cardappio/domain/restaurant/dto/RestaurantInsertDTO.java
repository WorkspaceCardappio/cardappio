package br.com.cardappio.domain.restaurant.dto;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantInsertDTO {

    @NotBlank(message = Messages.EMPTY_NAME)
    @Size(max = 255, message = Messages.SIZE_255)
    private String name;

    private Boolean active = true;

    @NotBlank(message = Messages.EMPTY_DOCUMENT)
    @Size(max = 14, message = Messages.SIZE_14)
    private String cnpj;
}
