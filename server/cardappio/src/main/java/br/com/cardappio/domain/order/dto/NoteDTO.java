package br.com.cardappio.domain.order.dto;

import org.hibernate.validator.constraints.Length;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;

public record NoteDTO(

        @NotBlank(message = Messages.EMPTY_NOTE)
        @Length(max = 255, message = Messages.SIZE_255)
        String note
) {
}