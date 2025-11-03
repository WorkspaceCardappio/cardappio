import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { DatePipe, CommonModule } from '@angular/common';
import { finalize } from 'rxjs';

import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';

import { StockService } from '../service/stock.service';
import { Stock } from '../../../model/stock';

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
    RouterModule

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
      .pipe(
        finalize(() => {
          this.loading = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (response) => {
          this.stocks = response.content;
          this.totalRecords = response.totalElements;
        },
        error: () => {},
      });
  }
}
