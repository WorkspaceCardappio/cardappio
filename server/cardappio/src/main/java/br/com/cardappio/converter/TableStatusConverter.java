package br.com.cardappio.converter;

import br.com.cardappio.enums.table.status.TableStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

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
