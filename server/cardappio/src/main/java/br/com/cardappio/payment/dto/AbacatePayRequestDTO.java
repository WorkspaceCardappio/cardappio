package br.com.cardappio.payment.dto;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public record AbacatePayRequestDTO(

        @NotNull(message = Messages.TICKET_NOT_NULL)
        UUID ticketId,

        @NotBlank(message = Messages.EMPTY_NOTE)
        @Size(max = 255, message = Messages.SIZE_255)
        String description,

        @NotNull(message = Messages.EMPTY_PRICE)
        @Min(value = 1, message = Messages.MIN_VALUE_ONE)
        BigDecimal amount,

        @NotBlank(message = Messages.EMPTY_NAME)
        @Size(max = 100, message = Messages.SIZE_255)
        String customerName,

        @NotBlank(message = Messages.INVALID_EMAIL)
        @Email(message = Messages.INVALID_EMAIL)
        String customerEmail,

        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(regexp = "^[0-9]{10,11}$", message = "O telefone deve conter 10 ou 11 dígitos numéricos.")
        String customerCellphone,

        @NotBlank(message = Messages.EMPTY_DOCUMENT)
        @Pattern(regexp = "\\d+", message = "O documento deve conter apenas números.")
        @Size(min = 11, max = 14, message = Messages.SIZE_14)
        String customerTaxId
) {
}