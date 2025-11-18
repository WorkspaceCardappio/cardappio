package br.com.cardappio.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersByPeriodDTO {
    private List<String> labels;
    private List<Long> data;
}
