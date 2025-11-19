import { Component, OnInit, PLATFORM_ID, Inject, ViewChildren, QueryList, ChangeDetectorRef } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration } from 'chart.js';
import { Chart } from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import { CardModule } from 'primeng/card';
import { Select } from 'primeng/select';
import { Skeleton } from 'primeng/skeleton';
import { FormsModule } from '@angular/forms';
import { DashboardService } from '../../core/services/dashboard.service';
import { DashboardStats } from '../../core/models/dashboard.model';
import { forkJoin } from 'rxjs';
import { finalize, catchError } from 'rxjs/operators';
import { WebSocketService } from '../../core/services/websocket.service';
import { of } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, BaseChartDirective, CardModule, Select, Skeleton, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  @ViewChildren(BaseChartDirective) charts!: QueryList<BaseChartDirective>;
  isBrowser: boolean;

  isLoadingStats = true;
  isLoadingChart = true;

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

  public statusDoughnutChartOptions: any = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'right'
      },
      tooltip: {
        callbacks: {
          label: (context: any) => {
            const label = context.label || '';
            const value = context.parsed || 0;
            const dataset = context.dataset.data as number[];
            const total = dataset.reduce((acc: number, val: number) => acc + val, 0);
            const percentage = total > 0 ? ((value / total) * 100).toFixed(1) : '0.0';
            return `${label}: ${value} (${percentage}%)`;
          }
        }
      },
      datalabels: {
        color: '#fff',
        font: {
          weight: 'bold',
          size: 14
        },
        formatter: (value: any, context: any) => {
          const dataset = context.dataset.data as number[];
          const total = dataset.reduce((acc: number, val: number) => acc + val, 0);
          const percentage = total > 0 ? ((value / total) * 100).toFixed(1) : '0.0';
          return `${value}\n(${percentage}%)`;
        }
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

  chartViewOptions = [
    { label: 'Pedidos ao longo do tempo', value: 'orders' },
    { label: 'Status dos Pedidos', value: 'status' },
    { label: 'Faturamento por período', value: 'revenue' },
    { label: 'Top Produtos por Receita', value: 'topProducts' }
  ];
  selectedChartView = 'orders';

  periodOptions = [
    { label: '7 dias', value: 7 },
    { label: '15 dias', value: 15 },
    { label: '30 dias', value: 30 },
    { label: '60 dias', value: 60 },
    { label: '90 dias', value: 90 }
  ];

  topProductsLimitOptions = [
    { label: 'Top 5', value: 5 },
    { label: 'Top 10', value: 10 },
    { label: 'Top 15', value: 15 },
    { label: 'Top 20', value: 20 }
  ];

  selectedPeriod = 7;
  selectedTopProductsLimit = 5;
  wsConnected = false;

  constructor(
    private dashboardService: DashboardService,
    private cdr: ChangeDetectorRef,
    private readonly webSocketService: WebSocketService,
    @Inject(PLATFORM_ID) platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(platformId);

    if (this.isBrowser) {
      Chart.register(ChartDataLabels);
    }
  }

  ngOnInit(): void {
    if (this.isBrowser) {
      this.loadDashboardData();
      this.subscribeToUpdates();
    }
  }

  private subscribeToUpdates(): void {
    this.webSocketService.getOrderEvents()
      .pipe(
        catchError(error => {
          console.error('Erro ao processar evento de pedido no dashboard:', error);
          return of(null);
        })
      )
      .subscribe(event => {
        if (!event) return;
        console.log('Dashboard: Atualizando gráficos após evento de pedido:', event.eventType);
        this.loadDashboardData();
      });

    this.webSocketService.getTicketEvents()
      .pipe(
        catchError(error => {
          console.error('Erro ao processar evento de comanda no dashboard:', error);
          return of(null);
        })
      )
      .subscribe(event => {
        if (!event) return;
        console.log('Dashboard: Atualizando gráficos após evento de comanda:', event.eventType);
        this.loadDashboardData();
      });

    this.webSocketService.getConnectionStatus()
      .pipe(
        catchError(error => {
          console.error('Erro ao monitorar status de conexão no dashboard:', error);
          return of(false);
        })
      )
      .subscribe(connected => {
        this.wsConnected = connected;
        this.cdr.markForCheck();
      });
  }

  loadDashboardData(): void {
    this.isLoadingStats = true;
    this.dashboardService.getDashboardStats()
      .pipe(finalize(() => {
        this.isLoadingStats = false;
        this.cdr.markForCheck();
      }))
      .subscribe({
        next: (stats) => {
          this.stats = stats;
        },
        error: (error) => {
          console.error('Erro ao carregar estatísticas:', error);
        }
      });

    this.isLoadingChart = true;
    forkJoin({
      orders: this.dashboardService.getOrdersByPeriod(this.selectedPeriod),
      revenue: this.dashboardService.getRevenueByPeriod(this.selectedPeriod),
      status: this.dashboardService.getOrdersByStatus(),
      topProducts: this.dashboardService.getTopProducts(this.selectedTopProductsLimit)
    })
    .pipe(finalize(() => {
      this.isLoadingChart = false;
      this.cdr.markForCheck();
    }))
    .subscribe({
      next: (data) => {
        this.ordersLineChartData = {
          labels: data.orders.labels,
          datasets: [
            {
              data: data.orders.data,
              label: 'Pedidos',
              fill: true,
              tension: 0.4,
              borderColor: '#4F46E5',
              backgroundColor: 'rgba(79, 70, 229, 0.1)'
            }
          ]
        };

        this.revenueBarChartData = {
          labels: data.revenue.labels,
          datasets: [
            {
              data: data.revenue.data,
              label: 'Faturamento (R$)',
              backgroundColor: '#10B981',
              borderColor: '#059669',
              borderWidth: 1
            }
          ]
        };

        this.statusDoughnutChartData = {
          labels: data.status.map(item => item.status),
          datasets: [
            {
              data: data.status.map(item => item.count),
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

        this.topProductsBarChartData = {
          labels: data.topProducts.map(item => item.productName),
          datasets: [
            {
              data: data.topProducts.map(item => item.revenue),
              label: 'Receita (R$)',
              backgroundColor: '#F59E0B',
              borderColor: '#D97706',
              borderWidth: 1
            }
          ]
        };

        this.updateCharts();
      },
      error: (error) => {
        console.error('Erro ao carregar gráficos:', error);
      }
    });
  }

  onPeriodChange(): void {
    this.isLoadingChart = true;
    forkJoin({
      orders: this.dashboardService.getOrdersByPeriod(this.selectedPeriod),
      revenue: this.dashboardService.getRevenueByPeriod(this.selectedPeriod)
    })
    .pipe(finalize(() => {
      this.isLoadingChart = false;
      this.cdr.markForCheck();
    }))
    .subscribe({
      next: (data) => {
        this.ordersLineChartData = {
          labels: data.orders.labels,
          datasets: [
            {
              data: data.orders.data,
              label: 'Pedidos',
              fill: true,
              tension: 0.4,
              borderColor: '#4F46E5',
              backgroundColor: 'rgba(79, 70, 229, 0.1)'
            }
          ]
        };

        this.revenueBarChartData = {
          labels: data.revenue.labels,
          datasets: [
            {
              data: data.revenue.data,
              label: 'Faturamento (R$)',
              backgroundColor: '#10B981',
              borderColor: '#059669',
              borderWidth: 1
            }
          ]
        };

        this.updateCharts();
      },
      error: (error) => {
        console.error('Erro ao atualizar gráficos por período:', error);
      }
    });
  }

  onTopProductsLimitChange(): void {
    this.isLoadingChart = true;
    this.dashboardService.getTopProducts(this.selectedTopProductsLimit)
      .pipe(finalize(() => {
        this.isLoadingChart = false;
        this.cdr.markForCheck();
      }))
      .subscribe({
        next: (data) => {
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
        },
        error: (error) => {
          console.error('Erro ao atualizar top produtos:', error);
        }
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
