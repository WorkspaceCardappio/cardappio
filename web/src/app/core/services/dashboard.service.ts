import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  DashboardStats,
  OrdersByPeriod,
  RevenueByPeriod,
  TopProducts,
  OrdersByStatus
} from '../models/dashboard.model';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getDashboardStats(): Observable<DashboardStats> {
    return this.http.get<DashboardStats>(`${this.apiUrl}/dashboard/stats`);
  }

  getOrdersByPeriod(days: number = 7): Observable<OrdersByPeriod> {
    return this.http.get<OrdersByPeriod>(`${this.apiUrl}/dashboard/orders-by-period?days=${days}`);
  }

  getRevenueByPeriod(days: number = 7): Observable<RevenueByPeriod> {
    return this.http.get<RevenueByPeriod>(`${this.apiUrl}/dashboard/revenue-by-period?days=${days}`);
  }

  getTopProducts(limit: number = 5): Observable<TopProducts[]> {
    return this.http.get<TopProducts[]>(`${this.apiUrl}/dashboard/top-products?limit=${limit}`);
  }

  getOrdersByStatus(): Observable<OrdersByStatus[]> {
    return this.http.get<OrdersByStatus[]>(`${this.apiUrl}/dashboard/orders-by-status`);
  }
}
