package br.com.cardappio.domain.order.dto;

import br.com.cardappio.utils.EnumDTO;
import br.com.cardappio.utils.IdDTO;

import java.util.List;

public record FlutterCreateOrderDTO(
        IdDTO ticket,
        EnumDTO status,
        List<FlutterCreateOrderItemDTO> items
) {
}
