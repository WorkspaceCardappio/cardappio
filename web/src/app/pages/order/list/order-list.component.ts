import { CurrencyPipe, DatePipe } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { Loader } from '../../../model/loader';
import LoadUtils from '../../../utils/load-utils';
import { RequestUtils } from '../../../utils/request-utils';
import { OrderStatusService } from '../service/order-status.service';
import { OrderService } from '../service/order.service';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [
    TableModule,
    RouterLink,
    CurrencyPipe,
    DatePipe,
    TagModule,
    BreadcrumbModule,
    ButtonModule,
    IconFieldModule,
    InputIconModule,
    InputTextModule,
    FormsModule,
    DialogModule,
    AutoCompleteModule,
    ReactiveFormsModule
],
  providers: [
    OrderStatusService
  ],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.scss',
})
export class OrderListComponent implements OnInit {
  orders: any[] = [];
  totalRecords = 0;
  loading = false;
  totalById: Map<string, number> = new Map();

  home = { icon: 'pi pi-home', routerLink: '/home' };
  items = [{ label: 'Pedidos', routerLink: '/order' }];

  visibleChangeStatus = false;
  idToChangeStatus: string | null = null;

  status: Loader = { values: [] };

  form: FormGroup<any> = new FormGroup({});

  ngOnInit(): void {
    this.loadOrders(LoadUtils.getDefault());
    this.findStatus();

    this.form = this.builder.group({
      status: ['', Validators.required]
    })
  }

  constructor(
    private service: OrderService,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private readonly statusService: OrderStatusService,
    private readonly builder: FormBuilder
  ) {}

  loadOrders(event: TableLazyLoadEvent) {
    this.loading = true;
    let request = RequestUtils.build(event);
    request += `&search=saveStatus==FINALIZED`;

    this.service.findAllDTO(request).subscribe({
      next: (response) => {
        this.orders = response.content;
        this.totalRecords = response.totalElements;
        this.findTotalByIds(this.orders.map((order) => order.id));
      },
      error: () => (this.orders = []),
      complete: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  onEdit(id: any) {
    this.router.navigate(['order', id]);
  }

  onDelete(id: any) {
    this.service
      .delete(id)
      .subscribe(() => this.loadOrders(LoadUtils.getDefault()));
  }

  findTotalByIds(ids: string[]) {
    this.service.findTotalByIds(ids).subscribe({
      next: (values: any[]) => {
        values.forEach((value) => {
          if (!this.totalById.has(value.id))
            this.totalById.set(value.id, value.total);
        });
      },
      complete: () => {
        this.cdr.markForCheck();
      },
    });
  }

  searchStatus() {
    this.status.values = [...this.status.values];
  }

  findStatus() {
    this.statusService.findAll().subscribe({
      next: (data) => {
        this.status.values = data;

        const defaultValue = data.find((item) => item.code === 1);
        const statusForm = this.form.get('status');

        if (defaultValue && !statusForm?.value) {
          statusForm?.patchValue(defaultValue);
        }
      },
      error: (err) => console.error('Erro ao buscar status', err),
      complete: () => this.cdr.markForCheck(),
    });
  }

  openChangeStatus(id: string) {
    this.visibleChangeStatus = true;
    this.idToChangeStatus = id;
  }

  changeStatus() {
    this.service
      .changeStatus(this.idToChangeStatus!, this.form.get('status')?.value?.code)
      .subscribe({
        next: () => {
          this.visibleChangeStatus = false;
          this.idToChangeStatus = null;
          this.loadOrders(LoadUtils.getDefault());
          this.form.reset();
        }
      })
  }
}
