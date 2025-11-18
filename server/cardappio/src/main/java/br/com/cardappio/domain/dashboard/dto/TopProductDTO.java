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
public class TopProductDTO {
    private String productName;
    private Long quantity;
    private BigDecimal revenue;
}
