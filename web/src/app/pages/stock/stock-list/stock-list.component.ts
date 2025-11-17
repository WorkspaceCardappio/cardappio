import { CommonModule, DatePipe } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';

import { BreadcrumbModule } from 'primeng/breadcrumb';
import { Stock } from '../../../model/stock';
import { StockService } from '../service/stock.service';

@Component({
  selector: 'app-stock',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    IconFieldModule,
    InputIconModule,
    InputTextModule,
    DatePipe,
    RouterModule,
    BreadcrumbModule
],
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.scss'],
  providers: [StockService],
})
export class StockListComponent implements OnInit {
  home = { icon: 'pi pi-home', routerLink: '/home' };
  items = [{ label: 'Estoques', routerLink: '/stocks' }];

  stocks: Stock[] = [];
  totalRecords = 0;
  loading = true;

  constructor(
    private service: StockService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.loadStocks({ first: 0, rows: 20, sortField: '', sortOrder: 1, filters: {} });
    this.loading = false;
  }

  clear(table: any) {
    table.clear();
  }

  onEdit(id: any) {
    this.router.navigate([`stock`, id]);
  }

  onDelete(id: any) {
    this.service.delete(id).subscribe(() =>
      this.loadStocks({ first: 0, rows: 20, sortField: '', sortOrder: 1, filters: {} })
    );
  }

  loadStocks(event: TableLazyLoadEvent) {

    this.loading = true;

    const page = (event.first ?? 0) / (event?.rows ?? 20);
    const size = event.rows ?? 20;
    const sortField = event.sortField ?? '';
    const sortOrder = event.sortOrder === 1 ? 'asc' : 'desc';

    let request = `page=${page}&size=${size}`;
    if (sortField) request += `&sort=${sortField},${sortOrder}`;

    this.service
      .findAllDTO(request)
      .subscribe({
        next: (response) => {
          this.stocks = response.content.map((s: any) => ({
            ...s,
            name: s.ingredientName ?? s.name,
            lote: (s.number !== undefined && s.number !== null) ? s.number : s.lote,
          }));
          this.totalRecords = response.totalElements;
        },
        complete: () => {
          this.loading = false;
          this.cdr.markForCheck();
        }
      });
  }
}



