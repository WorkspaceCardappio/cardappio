package br.com.cardappio.domain.dashboard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.cardappio.domain.dashboard.dto.DashboardStatsDTO;
import br.com.cardappio.domain.dashboard.dto.OrdersByPeriodDTO;
import br.com.cardappio.domain.dashboard.dto.OrdersByStatusDTO;
import br.com.cardappio.domain.dashboard.dto.RevenueByPeriodDTO;
import br.com.cardappio.domain.dashboard.dto.TopProductDTO;
import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.OrderRepository;
import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.domain.save.SaveStatus;
import br.com.cardappio.domain.ticket.TicketRepository;
import br.com.cardappio.enums.OrderStatus;
import br.com.cardappio.enums.TicketStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;

    public DashboardStatsDTO getDashboardStats() {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        LocalDateTime endOfToday = LocalDate.now().plusDays(1).atStartOfDay();

        List<Order> allOrders = orderRepository.findAllBySaveStatusWithProductOrders(SaveStatus.FINALIZED);

        List<Order> todayOrders = allOrders.stream()
                .filter(order -> order.getCreatedAt().isAfter(startOfToday) &&
                        order.getCreatedAt().isBefore(endOfToday))
                .toList();

        long totalOrders = allOrders.size();
        BigDecimal totalRevenue = calculateTotalRevenue(allOrders);
        BigDecimal averageTicket = totalOrders > 0 ?
                totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        Long ordersToday = (long) todayOrders.size();
        BigDecimal revenueToday = calculateTotalRevenue(todayOrders);

        Long openTables = ticketRepository.countByStatus(TicketStatus.OPEN);

        return new DashboardStatsDTO(
                totalOrders,
                totalRevenue,
                averageTicket,
                openTables,
                ordersToday,
                revenueToday
        );
    }

    public OrdersByPeriodDTO getOrdersByPeriod(int days) {
        LocalDateTime startDate = LocalDate.now().minusDays(days - 1).atStartOfDay();

        List<Order> orders = orderRepository.findAllBySaveStatus(SaveStatus.FINALIZED).stream()
                .filter(order -> order.getCreatedAt() != null && order.getCreatedAt().isAfter(startDate))
                .toList();

        Map<LocalDate, Long> ordersByDate = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().toLocalDate(),
                        Collectors.counting()
                ));

        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            labels.add(date.format(formatter));
            data.add(ordersByDate.getOrDefault(date, 0L));
        }

        return new OrdersByPeriodDTO(labels, data);
    }

    public RevenueByPeriodDTO getRevenueByPeriod(int days) {
        LocalDateTime startDate = LocalDate.now().minusDays(days - 1).atStartOfDay();

        List<Order> orders = orderRepository.findAllBySaveStatusWithProductOrders(SaveStatus.FINALIZED).stream()
                .filter(order -> order.getCreatedAt() != null && order.getCreatedAt().isAfter(startDate))
                .toList();

        Map<LocalDate, BigDecimal> revenueByDate = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCreatedAt().toLocalDate(),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                this::calculateOrderRevenue,
                                BigDecimal::add
                        )
                ));

        List<String> labels = new ArrayList<>();
        List<BigDecimal> data = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            labels.add(date.format(formatter));
            data.add(revenueByDate.getOrDefault(date, BigDecimal.ZERO));
        }

        return new RevenueByPeriodDTO(labels, data);
    }

    public List<TopProductDTO> getTopProducts(int limit) {
        List<Order> orders = orderRepository.findAllBySaveStatusWithProductOrders(SaveStatus.FINALIZED);

        Map<String, ProductStats> productStatsMap = new HashMap<>();

        for (Order order : orders) {
            if (order.getProductOrders() != null && !order.getProductOrders().isEmpty()) {
                for (ProductOrder productOrder : order.getProductOrders()) {
                    if (productOrder.getProductItem() != null &&
                            productOrder.getProductItem().getProduct() != null) {
                        String productName = productOrder.getProductItem().getProduct().getName();
                        BigDecimal quantity = productOrder.getQuantity();
                        BigDecimal price = productOrder.getProductItem().getPrice();
                        BigDecimal revenue = price.multiply(quantity);

                        productStatsMap.compute(productName, (key, stats) -> {
                            if (stats == null) {
                                return new ProductStats(quantity, revenue);
                            } else {
                                return new ProductStats(
                                        stats.quantity.add(quantity),
                                        stats.revenue.add(revenue)
                                );
                            }
                        });
                    }
                }
            }
        }

        return productStatsMap.entrySet().stream()
                .map(entry -> new TopProductDTO(
                        entry.getKey(),
                        entry.getValue().quantity.longValue(),
                        entry.getValue().revenue
                ))
                .sorted(Comparator.comparing(TopProductDTO::getRevenue).reversed())
                .limit(limit)
                .toList();
    }

    public List<OrdersByStatusDTO> getOrdersByStatus() {
        List<Order> orders = orderRepository.findAllBySaveStatus(SaveStatus.FINALIZED);
        long totalOrders = orders.size();

        Map<OrderStatus, Long> ordersByStatus = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getStatus,
                        Collectors.counting()
                ));

        return ordersByStatus.entrySet().stream()
                .map(entry -> {
                    Long count = entry.getValue();
                    BigDecimal percentage = totalOrders > 0 ?
                            BigDecimal.valueOf(count * 100.0 / totalOrders)
                                    .setScale(2, RoundingMode.HALF_UP) :
                            BigDecimal.ZERO;

                    return new OrdersByStatusDTO(
                            entry.getKey().getDescription(),
                            count,
                            percentage
                    );
                })
                .sorted(Comparator.comparing(OrdersByStatusDTO::getCount).reversed())
                .toList();
    }

    private BigDecimal calculateTotalRevenue(List<Order> orders) {
        return orders.stream()
                .map(this::calculateOrderRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateOrderRevenue(Order order) {
        return order.getProductOrders().stream()
                .map(po -> po.getProductItem().getPrice().multiply(po.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private record ProductStats(BigDecimal quantity, BigDecimal revenue) {
    }
}
