package br.com.cardappio.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersByStatusDTO {
    private String status;
    private Long count;
    private BigDecimal percentage;
}
