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
import { SkeletonModule } from 'primeng/skeleton';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { Loader } from '../../../model/loader';
import LoadUtils from '../../../utils/load-utils';
import { RequestUtils } from '../../../utils/request-utils';
import { OrderService } from '../../order/service/order.service';
import { TicketService } from '../service/ticket.service';
import { WebSocketService } from '../../../core/services/websocket.service';
import { catchError, of } from 'rxjs';

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
    MessageModule,
    SkeletonModule
  ],
  providers: [TicketService],
  templateUrl: './ticket-list.component.html',
  styleUrl: './ticket-list.component.scss',
})
export class TicketListComponent implements OnInit {

  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [{ label: 'Comandas', routerLink: '/ticket' }];

  tickets: any[] = [];
  totalRecords: number = 0;
  loading = false;
  loadingTotal = false;
  loadingTotalOrders = false;
  loadingTotalOrdersToSplit = false;

  expandedRows: { [key: string]: boolean } = {};
  products: any[] = [];

  pathNew = '/ticket/new';
  visibleSplit = false;

  selectedOrders: any[] = [];
  ordersToSplit: any[] = [];
  totalRecordsSplit: number = 0;
  ticketToSplit: any | null = null;
  splitLoading = false;

  ticketsToOption: Loader = { values: [] };

  totalById: Map<string, number> = new Map();
  totalOrdersById: Map<string, number> = new Map();

  ticketOptions = [
    { label: 'Nova comanda', value: 'criar' },
    { label: 'Vincular comanda', value: 'vincular' }
  ];

  optionsSelected: string = 'criar';
  selectedTicketToSend: any = null;
  wsConnected = false;

  constructor(
    private service: TicketService,
    private orderService: OrderService,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private readonly webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {
    this.load(LoadUtils.getDefaultForTicket());
    this.subscribeToTicketChanges();
  }

  private subscribeToTicketChanges(): void {
    this.webSocketService.getTicketEvents()
      .pipe(
        catchError(error => {
          console.error('Erro ao processar evento de comanda:', error);
          return of(null);
        })
      )
      .subscribe(event => {
        if (!event) return;
        console.log('Ticket event received in component:', event);
        this.load(LoadUtils.getDefaultForTicket());
      });

    this.webSocketService.getConnectionStatus()
      .pipe(
        catchError(error => {
          console.error('Erro ao monitorar status de conexão:', error);
          return of(false);
        })
      )
      .subscribe(connected => {
        this.wsConnected = connected;
        this.cdr.markForCheck();
      });
  }

  clear(table: any) {
    table.clear();
  }

  onEdit(id: any) {
    this.router.navigate([`ticket`, id]);
  }

  onDelete(id: any) {
    this.service.delete(id).subscribe(() => this.load(LoadUtils.getDefaultForTicket()));
  }

  load(event: TableLazyLoadEvent) {
    this.loading = true;

    const request = RequestUtils.build(event);

    this.service.findAllDTO(request).subscribe({
      next: (response) => {
        this.tickets = response.content;
        this.totalRecords = response.totalElements;
        this.findTotalByIds(this.tickets.map(ticket => ticket.id));
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
      sortField: 'number',
      sortOrder: -1,
    };

    const request = RequestUtils.build(event);

    this.orderService.findToTicket(this.ticketToSplit?.id, request).subscribe({
      next: (response) => {
        this.ordersToSplit = response.content;
        this.totalRecordsSplit = response.totalElements;
        this.findTotalOrdersByIds(this.ordersToSplit.map(order => order.id), this.loadingTotalOrdersToSplit);
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
        const orders = response.content;
        ticket.orders = orders;
        ticket.totalOrders = response.totalElements;
        this.findTotalOrdersByIds(orders.map(order => order.id), this.loadingTotalOrders);
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

  showSplit(ticket: any) {
    this.visibleSplit = true;
    this.ticketToSplit = ticket;
    this.loadOrdersToSplit();
  }

  closeSplit() {
    this.ticketToSplit = null;
    this.visibleSplit = false;
    this.selectedOrders = [];
  }

  confirmSplit() {

    const ids = this.selectedOrders.map(order => order.id);

    const ticketToSend = this.optionsSelected === 'vincular'
      ? this.selectedTicketToSend?.id
      : null;

    this.service.split(this.ticketToSplit?.id, ids, ticketToSend)
      .subscribe(() => {
        this.closeSplit();
        this.load(LoadUtils.getDefaultForTicket());
      });
  }

  getSelectedTotal(): number {
    if (!this.selectedOrders || this.selectedOrders.length === 0) return 0;

    let total = 0;

    this.selectedOrders.forEach((order: any) => {
      total += this.totalOrdersById.get(order.id) || 0;
    });

    return total;
  }

  disabledConfirmSplit() {
    return !this.selectedOrders.length
      || this.selectedOrders.length === this.ordersToSplit.length
      || (this.optionsSelected === 'vincular' && !this.selectedTicketToSend);
  }

  searchTickets(): void {

    this.ticketsToOption.isLoading = true;

    let search = `&search=status==OPEN;id!=${this.ticketToSplit?.id};table.id==${this.ticketToSplit?.table?.id}`;
    const completeParams = `pageSize=100${search}`;

    this.service.findAllDTO(completeParams).subscribe({
      next: (response) => this.ticketsToOption.values = response.content,
      error: () => (this.ticketsToOption.values = []),
      complete: () => {
        this.ticketsToOption.isLoading = false;
        this.cdr.markForCheck();
      },
    });
  }

  findTotalByIds(ids: string[]) {

    this.loadingTotal = true;

    this.service
      .findTotalByIds(ids)
      .subscribe({
        next: (values: any[]) => {

          values.forEach(value => {
            if (!this.totalById.has(value.id))
              this.totalById.set(value.id, value.total);
          });

        },
        complete: () => {
          this.loadingTotal = false;
          this.cdr.markForCheck();
        }
      });

  }

  findTotalOrdersByIds(ids: string[], loading: boolean) {

    loading = true;

    this.orderService
      .findTotalByIds(ids)
      .subscribe({
        next: (values: any[]) => {

          values.forEach(value => {
            if (!this.totalOrdersById.has(value.id))
              this.totalOrdersById.set(value.id, value.total);
          });

        },
        complete: () => {
          loading = false;
          this.cdr.markForCheck();
        }
      });

  }
}
