import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { FieldsetModule } from 'primeng/fieldset';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { Table, TableLazyLoadEvent, TableModule } from 'primeng/table';
import { map, Observable } from 'rxjs';
import { StockService } from '../../stock/service/stock.service';
import { IngredientService } from '../service/ingredient.service';

@Component({
  selector: 'app-ingredients',
  imports: [
    TableModule,
    ButtonModule,
    IconFieldModule,
    InputIconModule,
    CommonModule,
    FormsModule,
    InputTextModule,
    RouterLink,
    BreadcrumbModule,
    SelectModule,
    FieldsetModule,
  ],
  providers: [IngredientService, StockService],
  templateUrl: './ingredient-list.component.html',
  styleUrl: './ingredient-list.component.scss',
})
export class IngredientsComponent implements OnInit {
  home = { icon: 'pi pi-home', routerLink: '/home' };
  items = [{ label: 'Ingredientes', routerLink: '/ingredients' }];
  @ViewChild('dt1') dt1!: Table;

  expandedRows: { [key: string]: boolean } = {};
  ingredients: any[] = [];
  totalRecords: number = 0;
  loading = false;

  constructor(
    private readonly service: IngredientService,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private readonly stockService: StockService
  ) {}

  ngOnInit(): void {
    this.loadIngredients({
      first: 0,
      rows: 20,
      sortField: '',
      sortOrder: 1,
      filters: {},
    });
  }

  clear(table: any) {
    table.clear();
  }

  onEdit(id: any) {
    this.router.navigate([`ingredient`, id]);
  }

  onDelete(id: any) {
    this.service.delete(id).subscribe(() =>
      this.loadIngredients({
        first: 0,
        rows: 20,
        sortField: '',
        sortOrder: 1,
        filters: {},
      })
    );
  }

  loadIngredients(event: TableLazyLoadEvent) {
    this.loading = true;

    const page = (event.first ?? 0) / (event?.rows ?? 20);
    const size = event.rows ?? 20;
    const sortField = event.sortField ?? '';
    const sortOrder = event.sortOrder === 1 ? 'asc' : 'desc';

    let request = `page=${page}&size=${size}`;

    if (sortField) {
      request += `&sort=${sortField},${sortOrder}`;
    }

    this.service.findAllDTO(request).subscribe({
      next: (response) => {
        this.ingredients = response.content;
        this.totalRecords = response.totalElements;
      },
      error: () => {},
      complete: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  unitOfMeasurement = (query: string): Observable<any[]> => {
    return this.service.getUnitOfMeasurement().pipe(
      map((units) => {
        const filtered = query
          ? units.filter((u) =>
              u.description.toLowerCase().includes(query.toLowerCase())
            )
          : units;

        return filtered;
      })
    );
  };

  loadStocks(event: TableLazyLoadEvent, ingredient: any) {
    ingredient.loadingStocks = true;

    const search = `search=ingredient.id==${ingredient.id}&pageSize=100`;

    this.stockService.findAllDTO(search).subscribe({
      next: (response: any) => {
        ingredient.stocks = response.content;
        ingredient.totalStock = response.totalElements;
      },
      complete: () => {
        ingredient.loadingStocks = false;
        this.cdr.markForCheck();
      },
    });
  }

  toggleRow(ingredient: any) {
    if (this.expandedRows[ingredient.id]) {
      delete this.expandedRows[ingredient.id];
      return;
    }

    this.expandedRows[ingredient.id] = true;
    this.cdr.markForCheck();

    if (!ingredient.stocks) {
      ingredient.stocks = [];
      ingredient.totalStocks = 0;
    }
  }
}
