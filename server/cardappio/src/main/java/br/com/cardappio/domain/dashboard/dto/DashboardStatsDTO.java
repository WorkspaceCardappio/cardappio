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
public class DashboardStatsDTO {
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private BigDecimal averageTicket;
    private Long openTables;
    private Long ordersToday;
    private BigDecimal revenueToday;
}
