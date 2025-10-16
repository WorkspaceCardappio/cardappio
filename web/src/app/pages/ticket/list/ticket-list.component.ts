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
import LoadUtils from '../../../utils/load-utils';
import { RequestUtils } from '../../../utils/request-utils';
import { TicketService } from '../service/ticket.service';

@Component({
  selector: 'app-ticket-list',
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
  providers: [
    TicketService
  ],
  templateUrl: './ticket-list.component.html',
  styleUrl: './ticket-list.component.scss'
})
export class TicketListComponent{

  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [{ label: 'Comanda', routerLink: '/ticket' }];

  tickets: any[] = [];
  totalRecords: number = 0;
  loading = false;

  expandedRows: { [key: string]: boolean } = {};

  constructor(
    private service: TicketService,
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
    this.router.navigate([`ticket`, id]);
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

  toggleRow(ticket: any) {

    if (this.expandedRows[ticket.id]) {
      delete this.expandedRows[ticket.id];
      return;
    }

    this.expandedRows[ticket.id] = true;

    if (!ticket.orders && !ticket.loadingOrders) {
      this.loadOrders(ticket);
    }
  }

  loadOrders(ticket: any) {
    ticket.loadingOrders = false;
    ticket.orders = [];
    return;

    // this.service.findProductsInMenu(menu.id).subscribe({
    //   next: (products) => {
    //     menu.products = products;
    //   },
    //   error: () => {
    //     menu.products = [];
    //   },
    //   complete: () => {
    //     menu.loadingOrders = false;
    //     this.cdr.markForCheck();
    //   },
    // });
  }

}
