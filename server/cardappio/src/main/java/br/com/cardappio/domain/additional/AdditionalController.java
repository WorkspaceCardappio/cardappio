package br.com.cardappio.domain.additional;

import br.com.cardappio.domain.additional.dto.AdditionalDTO;
import com.cardappio.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products-additional")
public class AdditionalController extends CrudController<Additional, UUID, AdditionalDTO, AdditionalDTO> {
}
