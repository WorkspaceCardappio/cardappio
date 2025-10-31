import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { StockService } from '../service/stock.service';
import { finalize } from 'rxjs';
import { Product } from '../../../model/product';
import { Stock } from '../../../model/stock';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { Router } from '@angular/router';
import { Button } from "primeng/button";
import { IconField } from "primeng/iconfield";
import { InputIcon } from "primeng/inputicon";
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-stock',
  imports: [TableModule, Button, IconField, InputIcon, DatePipe],
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.scss'],
  providers: [StockService],
})
export class StockListComponent implements OnInit {
  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [{ label: 'Estoques', routerLink: '/stocks' }];
  stocks: Stock[] = [];
  totalRecords: number = 0;
  loading: boolean = true;

  constructor(private service: StockService, private router: Router, private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    this.loadStocks({
      first: 0,
      rows: 20,
      sortField: '',
      sortOrder: 1,
      filters: {}
    });
    this.loading = false;
  }

  clear(table: any) {
    table.clear();
  }

  onEdit(id: any) {
    this.router.navigate([`stocks`, id]);
  }
  onDelete(id: any) {
    this.service.delete(id).subscribe(() => this.loadStocks({
      first: 0,
      rows: 20,
      sortField: '',
      sortOrder: 1,
      filters: {},
    }));
  }

  loadStocks(event: TableLazyLoadEvent) {
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
          this.stocks = response.content;
          this.totalRecords = response.totalElements;
        },
        error: () => { },
      });
  }
}
