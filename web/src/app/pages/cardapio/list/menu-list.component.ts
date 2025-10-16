import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import LoadUtils from '../../../utils/load-utils';
import { RequestUtils } from '../../../utils/request-utils';
import { MenuService } from '../service/menu.service';

@Component({
  selector: 'menu-list',
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
  providers: [MenuService],
  templateUrl: './menu-list.component.html',
  styleUrl: './menu-list.component.scss',
})
export class MenuListComponent {
  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [{ label: 'Cardápio', routerLink: '/menu' }];

  menus: any[] = [];
  totalRecords: number = 0;
  loading = false;

  expandedRows: { [key: string]: boolean } = {};
  products: any[] = [];

  constructor(
    private service: MenuService,
    private cdr: ChangeDetectorRef,
    private router: Router,
  private ngZone: NgZone
  ) {}

  ngOnInit(): void {
    this.load(LoadUtils.getDefault());
  }

  clear(table: any) {
    table.clear();
  }

  onEdit(id: any) {
    this.router.navigate([`menu`, id]);
  }

  onDelete(id: any) {
    this.service.delete(id).subscribe(() => this.load(LoadUtils.getDefault()));
  }

  load(event: TableLazyLoadEvent) {
    this.loading = true;

    const request = RequestUtils.build(event);

    this.service
      .findAllDTO(request)
      .subscribe({
        next: (response) => {
          this.menus = response.content;
          this.totalRecords = response.totalElements;
        },
        error: () => {},
        complete: () => {
          this.loading = false;
          this.cdr.markForCheck();
        }
      });
  }

  toggleRow(menu: any) {

    if (this.expandedRows[menu.id]) {
      delete this.expandedRows[menu.id];
      return;
    }

    this.expandedRows[menu.id] = true;

    if (!menu.products && !menu.loadingProducts) {
      this.loadProducts(menu);
    }
  }

  loadProducts(menu: any) {
    menu.loadingProducts = true;

    this.service.findProductsInMenu(menu.id).subscribe({
      next: (products) => {
        menu.products = products;
      },
      error: () => {
        menu.products = [];
      },
      complete: () => {
        menu.loadingProducts = false;
        this.cdr.markForCheck();
      },
    });
  }
}
