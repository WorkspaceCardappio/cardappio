
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
import { finalize, map, Observable } from 'rxjs';
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
    FieldsetModule
  ],
  providers: [ IngredientService],
  templateUrl: './ingredient-list.component.html',
  styleUrl: './ingredient-list.component.scss'
})
export class IngredientsComponent implements OnInit {
  home = { icon: 'pi pi-home', routerLink: '/home' };
  items = [{ label: 'Ingredientes', routerLink: '/ingredients' }];
  @ViewChild('dt1') dt1!: Table;
  
  expandedRows: any[] = [];
  ingredients: any[] = [];
  totalRecords: number = 0;
  loading = false;
  

  constructor(public service: IngredientService, private cdr: ChangeDetectorRef, private router: Router) { }

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
    this.service.delete(id).subscribe(() => this.loadIngredients({
      first: 0,
      rows: 20,
      sortField: '',
      sortOrder: 1,
      filters: {},
    }));
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
          this.ingredients = response.content;
          this.totalRecords = response.totalElements;
        },
        error: () => {},
      });
  }
  
  unitOfMeasurement = (query: string): Observable<any[]> => {
  return this.service.getUnitOfMeasurement().pipe(
    map(units => {
      const filtered = query
        ? units.filter(u => u.description.toLowerCase().includes(query.toLowerCase()))
        : units;

      return filtered;
    })
  );
  }

  toggleRow(ingredient: any) {
 const index = this.expandedRows.indexOf(ingredient.id);
  if (index >= 0) {
    this.expandedRows.splice(index, 1);
  } else {
    this.expandedRows.push(ingredient.id);
  }
}

}
