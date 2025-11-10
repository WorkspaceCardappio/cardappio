package br.com.cardappio.domain.ticket.dto;

import br.com.cardappio.domain.order.dto.FlutterOrderDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
@Getter
public class FlutterTicketDTO {

    private List<FlutterOrderDTO> orders = new ArrayList<>();
}
