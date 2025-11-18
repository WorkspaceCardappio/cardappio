import { Component, OnInit, PLATFORM_ID, Inject, ViewChildren, QueryList, ChangeDetectorRef } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration } from 'chart.js';
import { CardModule } from 'primeng/card';
import { DashboardService } from '../../core/services/dashboard.service';
import { DashboardStats } from '../../core/models/dashboard.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, BaseChartDirective, CardModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  @ViewChildren(BaseChartDirective) charts!: QueryList<BaseChartDirective>;
  isBrowser: boolean;

  stats: DashboardStats = {
    totalOrders: 0,
    totalRevenue: 0,
    averageTicket: 0,
    openTables: 0,
    ordersToday: 0,
    revenueToday: 0
  };

  public ordersLineChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'Pedidos',
        fill: true,
        tension: 0.4,
        borderColor: '#4F46E5',
        backgroundColor: 'rgba(79, 70, 229, 0.1)'
      }
    ]
  };

  public ordersLineChartOptions: ChartConfiguration<'line'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'top'
      }
    },
    scales: {
      y: {
        beginAtZero: true
      }
    }
  };

  public revenueBarChartData: ChartConfiguration<'bar'>['data'] = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'Faturamento (R$)',
        backgroundColor: '#10B981',
        borderColor: '#059669',
        borderWidth: 1
      }
    ]
  };

  public revenueBarChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'top'
      }
    },
    scales: {
      y: {
        beginAtZero: true
      }
    }
  };

  public statusDoughnutChartData: ChartConfiguration<'doughnut'>['data'] = {
    labels: [],
    datasets: [
      {
        data: [],
        backgroundColor: [
          '#FCD34D',
          '#60A5FA',
          '#34D399',
          '#A78BFA',
          '#F87171'
        ],
        hoverBackgroundColor: [
          '#FBBF24',
          '#3B82F6',
          '#10B981',
          '#8B5CF6',
          '#EF4444'
        ]
      }
    ]
  };

  public statusDoughnutChartOptions: ChartConfiguration<'doughnut'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'right'
      }
    }
  };

  public topProductsBarChartData: ChartConfiguration<'bar'>['data'] = {
    labels: [],
    datasets: [
      {
        data: [],
        label: 'Receita (R$)',
        backgroundColor: '#F59E0B',
        borderColor: '#D97706',
        borderWidth: 1
      }
    ]
  };

  public topProductsBarChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    indexAxis: 'y',
    plugins: {
      legend: {
        display: true,
        position: 'top'
      }
    },
    scales: {
      x: {
        beginAtZero: true
      }
    }
  };

  constructor(
    private dashboardService: DashboardService,
    private cdr: ChangeDetectorRef,
    @Inject(PLATFORM_ID) platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  ngOnInit(): void {
    if (this.isBrowser) {
      setTimeout(() => {
        this.loadDashboardData();
      }, 100);
    }
  }

  loadDashboardData(): void {
    this.dashboardService.getDashboardStats().subscribe(stats => {
      this.stats = stats;
    });

    this.dashboardService.getOrdersByPeriod(7).subscribe(data => {
      this.ordersLineChartData = {
        labels: data.labels,
        datasets: [
          {
            data: data.data,
            label: 'Pedidos',
            fill: true,
            tension: 0.4,
            borderColor: '#4F46E5',
            backgroundColor: 'rgba(79, 70, 229, 0.1)'
          }
        ]
      };
      this.updateCharts();
    });

    this.dashboardService.getRevenueByPeriod(7).subscribe(data => {
      this.revenueBarChartData = {
        labels: data.labels,
        datasets: [
          {
            data: data.data,
            label: 'Faturamento (R$)',
            backgroundColor: '#10B981',
            borderColor: '#059669',
            borderWidth: 1
          }
        ]
      };
      this.updateCharts();
    });

    this.dashboardService.getOrdersByStatus().subscribe(data => {
      this.statusDoughnutChartData = {
        labels: data.map(item => item.status),
        datasets: [
          {
            data: data.map(item => item.count),
            backgroundColor: [
              '#FCD34D',
              '#60A5FA',
              '#34D399',
              '#A78BFA',
              '#F87171'
            ],
            hoverBackgroundColor: [
              '#FBBF24',
              '#3B82F6',
              '#10B981',
              '#8B5CF6',
              '#EF4444'
            ]
          }
        ]
      };
      this.updateCharts();
    });

    this.dashboardService.getTopProducts(5).subscribe(data => {
      this.topProductsBarChartData = {
        labels: data.map(item => item.productName),
        datasets: [
          {
            data: data.map(item => item.revenue),
            label: 'Receita (R$)',
            backgroundColor: '#F59E0B',
            borderColor: '#D97706',
            borderWidth: 1
          }
        ]
      };
      this.updateCharts();
    });
  }

  updateCharts(): void {
    setTimeout(() => {
      if (this.charts) {
        this.charts.forEach(chart => {
          chart.chart?.update();
        });
      }
      this.cdr.detectChanges();
    }, 0);
  }
}
