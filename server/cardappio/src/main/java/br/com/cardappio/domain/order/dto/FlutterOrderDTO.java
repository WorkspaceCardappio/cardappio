package br.com.cardappio.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class FlutterOrderDTO {

    private String name;

    private BigDecimal price;

    private Long quantity;


}
