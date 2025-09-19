package br.com.cardappio.converter;

import java.util.Optional;

import br.com.cardappio.enums.TableStatus;
import br.com.cardappio.enums.TicketStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.Table;

@Converter
public class TableStatusConverter implements AttributeConverter<TableStatus, Long> {

    @Override
    public Long convertToDatabaseColumn(final TableStatus status) {
        return Optional.ofNullable(status)
                .map(TableStatus::getCode)
                .orElse(null);
    }

    @Override
    public TableStatus convertToEntityAttribute(final Long code) {
        return Optional.ofNullable(code)
                .map(TableStatus::fromCode)
                .orElse(null);
    }
}
