package br.com.cardappio.domain.additional.adapter;

import br.com.cardappio.domain.additional.Additional;
import br.com.cardappio.domain.additional.dto.AdditionalDTO;
import com.cardappio.core.adapter.Adapter;

public class AdditionalAdapter implements Adapter<Additional, AdditionalDTO, AdditionalDTO> {

    @Override
    public AdditionalDTO toDTO(final Additional entity) {
        return new AdditionalDTO(entity);
    }

    @Override
    public Additional toEntity(final AdditionalDTO dto) {
        return Additional.of(dto);
    }
}
