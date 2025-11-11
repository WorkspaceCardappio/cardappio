import { CurrencyPipe, DatePipe } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import LoadUtils from '../../../utils/load-utils';
import { RequestUtils } from '../../../utils/request-utils';
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
export class OrderListComponent implements OnInit {

  orders: any[] = [];
  totalRecords = 0;
  loading = false;
  totalById: Map<string, number> = new Map();

  home = { icon: 'pi pi-home', routerLink: '/home' };
  items = [
    { label: 'Pedidos', routerLink: '/order' }
  ];

  ngOnInit(): void {
    this.loadOrders(LoadUtils.getDefault());
  }

  constructor(
    private service: OrderService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  loadOrders(event: TableLazyLoadEvent) {

    this.loading = true;
    let request = RequestUtils.build(event);
    request += `&search=saveStatus==FINALIZED`;

    this.service
      .findAllDTO(request)
      .subscribe({
        next: (response) => {
          this.orders = response.content;
          this.totalRecords = response.totalElements;
          this.findTotalByIds(this.orders.map(order => order.id));
        },
        error: () => this.orders = [],
        complete: () => {
          this.loading = false;
          this.cdr.markForCheck();
        }
      });
  }

  onEdit(id: any) {
    this.router.navigate(['order', id]);
  }

  onDelete(id: any) {
    this.service
      .delete(id)
      .subscribe(() => this.loadOrders(LoadUtils.getDefault()));
  }

  findTotalByIds(ids: string[]) {

    this.service
      .findTotalByIds(ids)
      .subscribe({
        next: (values: any[]) => {

          values.forEach(value => {
            if (!this.totalById.has(value.id))
              this.totalById.set(value.id, value.total);
          });

        },
        complete: () => {
            this.cdr.markForCheck();
        }
      });

  }
}
