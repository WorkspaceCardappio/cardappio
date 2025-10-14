import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { finalize } from 'rxjs';
import { CategoryService } from '../service/category.service';

@Component({
  selector: 'app-category',
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
  ],
  providers: [CategoryService],
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.scss',
})
export class CategoryComponent implements OnInit {

  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [{ label: 'Categorias', routerLink: '/category' }];

  categories: any[] = [];
  totalRecords: number = 0;
  loading = false;

  constructor(public service: CategoryService, private cdr: ChangeDetectorRef, private router: Router) {}

  ngOnInit(): void {
    this.loadCategories({
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
    this.router.navigate([`category`, id]);
  }

  onDelete(id: any) {
    this.service.delete(id).subscribe(() => this.loadCategories({
      first: 0,
      rows: 20,
      sortField: '',
      sortOrder: 1,
      filters: {},
    }));
  }

  loadCategories(event: TableLazyLoadEvent) {
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
          this.categories = response.content;
          this.totalRecords = response.totalElements;
        },
        error: () => {},
      });
  }
}
