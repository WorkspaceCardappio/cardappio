package br.com.cardappio.domain.dashboard;

import br.com.cardappio.domain.dashboard.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        DashboardStatsDTO stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/orders-by-period")
    public ResponseEntity<OrdersByPeriodDTO> getOrdersByPeriod(
            @RequestParam(defaultValue = "7") int days
    ) {
        OrdersByPeriodDTO data = dashboardService.getOrdersByPeriod(days);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/revenue-by-period")
    public ResponseEntity<RevenueByPeriodDTO> getRevenueByPeriod(
            @RequestParam(defaultValue = "7") int days
    ) {
        RevenueByPeriodDTO data = dashboardService.getRevenueByPeriod(days);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProductDTO>> getTopProducts(
            @RequestParam(defaultValue = "5") int limit
    ) {
        List<TopProductDTO> products = dashboardService.getTopProducts(limit);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/orders-by-status")
    public ResponseEntity<List<OrdersByStatusDTO>> getOrdersByStatus() {
        List<OrdersByStatusDTO> data = dashboardService.getOrdersByStatus();
        return ResponseEntity.ok(data);
    }
}
