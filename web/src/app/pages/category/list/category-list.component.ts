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
import LoadUtils from '../../../utils/load-utils';
import { RequestUtils } from '../../../utils/request-utils';
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
export class CategoryListComponent implements OnInit {

  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [{ label: 'Categoria', routerLink: '/category' }];

  categories: any[] = [];
  totalRecords: number = 0;
  loading = false;

  constructor(public service: CategoryService, private cdr: ChangeDetectorRef, private router: Router) {}

  ngOnInit(): void {
    this.loadCategories(LoadUtils.getDefault());
  }

  clear(table: any) {
    table.clear();
  }

  onEdit(id: any) {
    this.router.navigate([`category`, id]);
  }

  onDelete(id: any) {
    this.service.delete(id).subscribe(() => this.loadCategories(LoadUtils.getDefault()));
  }

  loadCategories(event: TableLazyLoadEvent) {

    this.loading = true;

    const request = RequestUtils.build(event);

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
