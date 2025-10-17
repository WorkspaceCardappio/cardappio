package br.com.cardappio.converter;

import java.util.Optional;

import br.com.cardappio.enums.PersonType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PersonTypeConverter implements AttributeConverter<PersonType, String> {

    @Override
    public String convertToDatabaseColumn(final PersonType status) {
        return Optional.ofNullable(status)
                .map(PersonType::getType)
                .orElse(null);
    }

    @Override
    public PersonType convertToEntityAttribute(final String type) {
        return Optional.ofNullable(type)
                .map(PersonType::fromType)
                .orElse(null);
    }
}
