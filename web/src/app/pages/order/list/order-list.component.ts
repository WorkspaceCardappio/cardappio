import { CurrencyPipe, DatePipe } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { finalize, switchMap, tap } from 'rxjs';
import { TicketService } from '../../ticket/service/ticket.service';
import { OrderService } from '../service/order.service';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [
    TableModule,
    RouterLink,
    CurrencyPipe,
    DatePipe,
    TagModule,
    BreadcrumbModule,
    ButtonModule,
    IconFieldModule,
    InputIconModule,
    InputTextModule,
    FormsModule,
  ],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.scss',
})
export class OrderListComponent {
  orders: any[] = [];
  totalRecords = 0;
  loading = false;

  home = { icon: 'pi pi-home', routerLink: '/home' };
  items = [{ label: 'Pedidos', routerLink: '/order' }];

  constructor(
    private orderService: OrderService,
    private ticketService: TicketService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  loadOrders(event: TableLazyLoadEvent) {
    this.loading = true;
    const page = (event.first ?? 0) / (event.rows ?? 20);
    const size = event.rows ?? 20;
    const sort = event.sortField
      ? `${event.sortField},${event.sortOrder === 1 ? 'asc' : 'desc'}`
      : '';
    const request = `page=${page}&size=${size}&sort=${sort}`;

    this.orderService
      .findAllDTO(request)
      .pipe(
        tap((response) => {
          this.orders = response.content;
          this.totalRecords = response.totalElements;
        }),
        switchMap((response) => {
          const ticketIds = [
            ...new Set(response.content.map((order: any) => order.ticketId)),
          ];
          if (ticketIds.length === 0) {
            return [];
          }
          const paramsRequest = `ids=${ticketIds.join(',')}`;
          return this.ticketService.findAllDTO(paramsRequest);
        }),
        finalize(() => {
          this.loading = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (ticketResponse) => {
          if (!ticketResponse?.content) return;
          const ticketMap = new Map<string, any>();
          ticketResponse.content.forEach((ticket: any) =>
            ticketMap.set(ticket.id, ticket)
          );
          this.orders.forEach((order) => {
            order.ticketDetails = ticketMap.get(order.ticketId);
          });
        },
        error: (err) =>
          console.error('Erro ao carregar pedidos ou comandas', err),
      });
  }

  onEdit(id: any) {
    this.router.navigate(['order', id]);
  }

  onDelete(id: any) {
    this.orderService
      .delete(id)
      .subscribe(() => this.loadOrders({ first: 0, rows: 20 }));
  }
}
