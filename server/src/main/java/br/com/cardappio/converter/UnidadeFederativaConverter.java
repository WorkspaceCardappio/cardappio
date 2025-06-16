package br.com.cardappio.converter;

import br.com.cardappio.enums.UnidadeFederativa;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter(autoApply = true)
public class UnidadeFederativaConverter implements AttributeConverter<UnidadeFederativa, String> {

    @Override
    public String convertToDatabaseColumn(UnidadeFederativa uf) {
        return Objects.nonNull(uf) ? uf.getSigla() : null;
    }

    @Override
    public UnidadeFederativa convertToEntityAttribute(String sigla) {
        return Objects.nonNull(sigla) ? UnidadeFederativa.fromSigla(sigla) : null;
    }
}
