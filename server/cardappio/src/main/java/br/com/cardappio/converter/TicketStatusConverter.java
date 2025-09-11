package br.com.cardappio.converter;

import java.util.Optional;

import br.com.cardappio.enums.TicketStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TicketStatusConverter implements AttributeConverter<TicketStatus, Long> {

    @Override
    public Long convertToDatabaseColumn(final TicketStatus status) {
        return Optional.ofNullable(status)
                .map(TicketStatus::getCode)
                .orElse(null);
    }

    @Override
    public TicketStatus convertToEntityAttribute(final Long code) {
        return Optional.ofNullable(code)
                .map(TicketStatus::fromCode)
                .orElse(null);
    }
}
