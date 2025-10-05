package br.com.cardappio.converter;

import br.com.cardappio.enums.PersonType;
import br.com.cardappio.enums.UnityOfMeasurement;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter
public class UnityOfMeasurementConverter implements AttributeConverter<UnityOfMeasurement, Long> {

    @Override
    public Long convertToDatabaseColumn(final UnityOfMeasurement status) {
        return Optional.ofNullable(status)
                .map(UnityOfMeasurement::getCode)
                .orElse(null);
    }

    @Override
    public UnityOfMeasurement convertToEntityAttribute(final Long code) {
        return Optional.ofNullable(code)
                .map(UnityOfMeasurement::fromCode)
                .orElse(null);
    }
}
