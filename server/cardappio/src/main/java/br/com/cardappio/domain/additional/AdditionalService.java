package br.com.cardappio.domain.additional;

import br.com.cardappio.domain.additional.adapter.AdditionalAdapter;
import br.com.cardappio.domain.additional.dto.AdditionalDTO;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdditionalService extends CrudService<Additional, UUID, AdditionalDTO, AdditionalDTO> {
    @Override
    protected Adapter<Additional, AdditionalDTO, AdditionalDTO> getAdapter() {
        return new AdditionalAdapter();
    }
}
