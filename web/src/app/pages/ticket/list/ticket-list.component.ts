import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
import { SelectButtonModule } from 'primeng/selectbutton';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { Loader } from '../../../model/loader';
import LoadUtils from '../../../utils/load-utils';
import { RequestUtils } from '../../../utils/request-utils';
import { OrderService } from '../../order/service/order.service';
import { TicketService } from '../service/ticket.service';

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
    TagModule,
    DialogModule,
    SelectButtonModule,
    AutoCompleteModule,
    MessageModule
  ],
  providers: [TicketService],
  templateUrl: './ticket-list.component.html',
  styleUrl: './ticket-list.component.scss',
})
export class TicketListComponent implements OnInit {

  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [{ label: 'Comanda', routerLink: '/ticket' }];

  tickets: any[] = [];
  totalRecords: number = 0;
  loading = false;

  expandedRows: { [key: string]: boolean } = {};
  products: any[] = [];

  pathNew = '/ticket/new';
  visibleSplit = false;

  selectedOrders: any[] = [];
  ordersToSplit: any[] = [];
  totalRecordsSplit: number = 0;
  ticketToSplit: string | null = null;
  splitLoading = false;

  ticketsToOption: Loader = { values: [] };

  ticketOptions = [
    { label: 'Nova comanda', value: 'criar' },
    { label: 'Vincular comanda', value: 'vincular' }
  ];

  optionsSelected: string = 'criar';
  selectedTicketToSplit: any = null;

  constructor(
    private service: TicketService,
    private orderService: OrderService,
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

    this.service.findAllDTO(request).subscribe({
      next: (response) => {
        this.tickets = response.content;
        this.totalRecords = response.totalElements;
      },
      error: () => {},
      complete: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  toggleRow(ticket: any) {
    if (this.expandedRows[ticket.id]) {
      delete this.expandedRows[ticket.id];
      return;
    }

    this.expandedRows[ticket.id] = true;

    if (!ticket.orders) {
      ticket.orders = [];
      ticket.totalOrders = 0;
    }
  }

  loadOrdersToSplit() {
    this.visibleSplit = true;
    this.splitLoading = true;

    const event: TableLazyLoadEvent = {
      first: 0,
      rows: 50,
      sortField: 'createdAt',
      sortOrder: -1,
    };

    const request = RequestUtils.build(event);

    this.orderService.findToTicket(this.ticketToSplit!, request).subscribe({
      next: (response) => {
        this.ordersToSplit = response.content;
        this.totalRecordsSplit = response.totalElements;
      },
      error: () => {
        this.ordersToSplit = [];
        this.totalRecordsSplit = 0;
      },
      complete: () => {
        this.splitLoading = false;
        this.cdr.markForCheck();
      },
    });
  }

  loadOrders(event: TableLazyLoadEvent, ticket: any) {
    ticket.loadingOrders = true;

    const request = RequestUtils.build(event);

    this.orderService.findToTicket(ticket.id, request).subscribe({
      next: (response) => {
        ticket.orders = response.content;
        ticket.totalOrders = response.totalElements;
      },
      error: () => {
        ticket.orders = [];
        ticket.totalOrders = 0;
      },
      complete: () => {
        ticket.loadingOrders = false;
        this.cdr.markForCheck();
      },
    });
  }

  redirectToOrder(id: string) {
    this.router.navigate([`order`, id]);
  }

  showSplit(id: string) {
    this.visibleSplit = true;
    this.ticketToSplit = id;
    this.loadOrdersToSplit();
  }

  closeSplit() {
    this.ticketToSplit = null;
    this.visibleSplit = false;
  }

  confirmSplit() {

    const ids = this.selectedOrders.map(order => order.id);

    const ticketToSplit = this.optionsSelected === 'vincular'
      ? this.selectedTicketToSplit?.id
      : null;

    this.service.split(this.ticketToSplit!, ids, ticketToSplit)
      .subscribe(() => {
        this.closeSplit();
        this.load(LoadUtils.getDefault());
      });
  }

  getSelectedTotal(): number {
    if (!this.selectedOrders || this.selectedOrders.length === 0) return 0;

    return this.selectedOrders.reduce((total, order) => {
      return total + (order.total || 0);
    }, 0);
  }

  disabledConfirmSplit() {
    return !this.selectedOrders.length
      || this.selectedOrders.length === this.ordersToSplit.length
      || (this.optionsSelected === 'vincular' && !this.selectedTicketToSplit);
  }

  searchTickets(event: any): void {

    this.ticketsToOption.isLoading = true;

    const query = event.query;

    let search = `&search=status==OPEN;id!=${this.ticketToSplit}`;
    const completeParams = `pageSize=100${search}`;

    this.service.findAllDTO(completeParams).subscribe({
      next: (page) => (this.ticketsToOption.values = page.content),
      error: () => (this.ticketsToOption.values = []),
      complete: () => {
        this.ticketsToOption.isLoading = false;
        this.cdr.markForCheck();
      },
    });
  }
}
