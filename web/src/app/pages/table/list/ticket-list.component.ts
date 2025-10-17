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
import { TicketService } from '../service/table.service';

@Component({
  selector: 'app-ticket',
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
    TagModule
  ],
  providers: [TicketService],
  templateUrl: './ticket-list.component.html',
  styleUrl: './ticket-list.component.scss',
})
export class TicketListComponent {

  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [{ label: 'Comanda', routerLink: '/ticket' }];

  tickets: any[] = [];
  totalRecords: number = 0;
  loading = false;

  expandedRows: { [key: string]: boolean } = {};
  products: any[] = [];

  pathNew = '/ticket/new';

  constructor(
    private service: TicketService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

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
          this.tickets = response.content;
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

    return;

    // this.service.findProductsInMenu(menu.id).subscribe({
    //   next: (products) => {
    //     menu.products = products;
    //   },
    //   error: () => {
    //     menu.products = [];
    //   },
    //   complete: () => {
    //     menu.loadingProducts = false;
    //     this.cdr.markForCheck();
    //   },
    // });
  }
}
