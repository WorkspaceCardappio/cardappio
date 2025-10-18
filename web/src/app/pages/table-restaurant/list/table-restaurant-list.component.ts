import { CommonModule } from '@angular/common';
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
import LoadUtils from '../../../utils/load-utils';
import { RequestUtils } from '../../../utils/request-utils';
import { TableRestaurantService } from '../service/table-restaurant.service';

@Component({
  selector: 'table-restaurant-list-component',
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
    TagModule,
  ],
  providers: [TableRestaurantService],
  templateUrl: 'table-restaurant-list.component.html',
  styleUrl: 'table-restaurant-list.component.scss',
})
export class TableRestaurantListComponent {
  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [{ label: 'Mesa', routerLink: '/table' }];

  tables: any[] = [];
  totalRecords: number = 0;
  loading = false;

  constructor(
    public service: TableRestaurantService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.load(LoadUtils.getDefault());
  }

  clear(table: any) {
    table.clear();
  }

  onEdit(id: any) {
    this.router.navigate([`table`, id]);
  }

  onDelete(id: any) {
    this.service.delete(id).subscribe(() => this.load(LoadUtils.getDefault()));
  }

  load(event: TableLazyLoadEvent) {
    this.loading = true;

    const request = RequestUtils.build(event);

    this.service.findAllDTO(request).subscribe({
      next: (response) => {
        this.tables = response.content;
        this.totalRecords = response.totalElements;
      },
      error: () => {},
      complete: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }
}
