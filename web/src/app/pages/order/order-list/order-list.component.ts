import { ChangeDetectorRef, Component } from '@angular/core';
import { OrderService } from "../service/order.service";
import { AutoComplete, AutoCompleteCompleteEvent } from "primeng/autocomplete";
import { FormsModule } from "@angular/forms";
import { Breadcrumb } from "primeng/breadcrumb";
import { Button, ButtonDirective } from "primeng/button";
import { IconField } from "primeng/iconfield";
import { InputIcon } from "primeng/inputicon";
import { InputText } from "primeng/inputtext";
import { TableLazyLoadEvent, TableModule } from "primeng/table";
import { finalize } from "rxjs";
import { Router, RouterLink } from "@angular/router";
import { CurrencyPipe, DatePipe } from "@angular/common";
import { Tag } from "primeng/tag";


@Component({
  selector: 'app-order-list',
  imports: [
    FormsModule,
    Breadcrumb,
    Button,
    ButtonDirective,
    IconField,
    InputIcon,
    InputText,
    TableModule,
    RouterLink,
    CurrencyPipe,
    DatePipe,
    Tag
  ],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.scss'
})
export class OrderListComponent {

  constructor(public service: OrderService, private cdr: ChangeDetectorRef, private router: Router) {}


  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [{ label: 'Orders', routerLink: '/order' }];

  orders: any[] = [];
  totalRecords: number = 0;
  loading = false;

  loadOrders(event: TableLazyLoadEvent) {
    this.loading = true;

    const page = (event.first ?? 0) / (event?.rows ?? 20);
    const size = event.rows ?? 20;
    const sortField = event.sortField ?? '';
    const sortOrder = event.sortOrder === 1 ? 'asc' : 'desc';

    let request = `page=${page}&size=${size}`;

    if (sortField) {
      request += `&sort=${sortField},${sortOrder}`;
    }

    this.service
      .findAllDTO(request)
      .pipe(
        finalize(() => {
          this.loading = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (response) => {
          this.orders = response.content;
          this.totalRecords = response.totalElements;
        },
        error: () => {},
      });
  }

  onEdit(id: any) {
    this.router.navigate([`category`, id]);
  }

  onDelete(id: any) {
    this.service.delete(id).subscribe(() => this.loadOrders({
      first: 0,
      rows: 20,
      sortField: '',
      sortOrder: 1,
      filters: {},
    }));
  }

}
