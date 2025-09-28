package br.com.cardappio.converter;

import java.util.Optional;

import br.com.cardappio.enums.PersonType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PersonTypeConverter implements AttributeConverter<PersonType, Long> {

    @Override
    public Long convertToDatabaseColumn(final PersonType status) {
        return Optional.ofNullable(status)
                .map(PersonType::getCode)
                .orElse(null);
    }

    @Override
    public PersonType convertToEntityAttribute(final Long code) {
        return Optional.ofNullable(code)
                .map(PersonType::fromCode)
                .orElse(null);
    }
}
