package br.com.cardappio.converter;

import br.com.cardappio.enums.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Long> {

    @Override
    public Long convertToDatabaseColumn(final OrderStatus status) {

        return Optional.ofNullable(status)
                .map(OrderStatus::getCode)
                .orElse(null);
    }

    @Override
    public OrderStatus convertToEntityAttribute(final Long code) {

        return Optional.ofNullable(code)
                .map(OrderStatus::fromCode)
                .orElse(null);
    }
}
