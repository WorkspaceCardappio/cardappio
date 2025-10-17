package br.com.cardappio.converter;

import java.util.Optional;

import br.com.cardappio.enums.ItemSize;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ItemTypeConverter implements AttributeConverter<ItemSize, Long> {

    @Override
    public Long convertToDatabaseColumn(final ItemSize status) {
        return Optional.ofNullable(status)
                .map(ItemSize::getCode)
                .orElse(null);
    }

    @Override
    public ItemSize convertToEntityAttribute(final Long code) {
        return Optional.ofNullable(code)
                .map(ItemSize::fromCode)
                .orElse(null);
    }
}
