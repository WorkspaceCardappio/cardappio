export interface DashboardStats {
  totalOrders: number;
  totalRevenue: number;
  averageTicket: number;
  openTables: number;
  ordersToday: number;
  revenueToday: number;
}

export interface OrdersByPeriod {
  labels: string[];
  data: number[];
}

export interface RevenueByPeriod {
  labels: string[];
  data: number[];
}

export interface TopProducts {
  productName: string;
  quantity: number;
  revenue: number;
}

export interface OrdersByStatus {
  status: string;
  count: number;
  percentage: number;
}
