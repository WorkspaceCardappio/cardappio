package br.com.cardappio.domain.ticket.dto;

import br.com.cardappio.domain.order.dto.FlutterOrderDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
@Getter
public class FlutterTicketDTO {

    private BigDecimal total;

    private List<FlutterOrderDTO> orders = new ArrayList<>();

    public FlutterTicketDTO(BigDecimal total) {

        this.total = total;
    }
}
