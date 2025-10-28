import { ChangeDetectorRef, Component, OnInit, signal, WritableSignal } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../../product/product.service';
import { TicketService } from '../../../ticket/service/ticket.service';
import { OrderService } from '../../service/order.service';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { Breadcrumb } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { DatePickerModule } from 'primeng/datepicker';
import { Fieldset } from 'primeng/fieldset';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { SelectButtonModule } from 'primeng/selectbutton';
import { StepperModule } from 'primeng/stepper';
import { TableModule } from 'primeng/table';
import { Loader } from '../../../../model/loader';
import { OrderGroupId } from '../../model/order-group-id.model';
import { OrderStatusService } from '../../service/order-status.service';
import { OrderAdditionalComponent } from '../order-additional/order-additional.component';
import { OrderOptionsComponent } from "../order-options/order-options.component";
import { OrderSummaryComponent } from "../order-summary/order-summary.component";
import { OrderVariableComponent } from '../order-variable/order-variable.component';

@Component({
  selector: 'app-order-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    AutoCompleteModule,
    InputNumberModule,
    ButtonModule,
    Fieldset,
    Breadcrumb,
    TableModule,
    StepperModule,
    SelectButtonModule,
    FormsModule,
    CommonModule,
    FloatLabelModule,
    DatePickerModule,
    InputTextModule,
    OrderOptionsComponent,
    OrderAdditionalComponent,
    OrderVariableComponent,
    OrderSummaryComponent
],
  providers: [OrderStatusService, TicketService, ProductService],
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.scss'],
})
export class OrderFormComponent implements OnInit {

  stepIndex = 1;

  id: string | null = null;
  isEditMode = false;

  form: FormGroup = new FormGroup({});

  tickets: Loader = { values: [] };
  products: Loader = { values: [] };
  status: Loader = { values: [] };

  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [
    { label: 'Pedidos', routerLink: '/order' },
    { label: 'Novo', routerLink: '/order/new' },
  ];

  orderGroupId: WritableSignal<OrderGroupId> = signal({
    order: null,
    product: null,
    item: null
  });

  constructor(
    private readonly orderService: OrderService,
    private readonly ticketService: TicketService,
    private readonly productService: ProductService,
    private readonly statusService: OrderStatusService,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly builder: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.form = this.initForm();
    this.findStatus();

    this.id = this.route.snapshot.paramMap.get('id');
    this.isEditMode = this.id !== 'new';

    if (this.isEditMode) {
      // this.loadOrder();
    }
  }

  private initForm() {
    const form = this.builder.group({
      id: [''],
      number: [null],
      status: [null, Validators.required],
      createdAt: [null],
      ticket: [null, Validators.required],
    });

    return form;
  }

  searchProducts(event: any): void {
    const query = event.query || '';
    const searches = query ? [`name=ilike=${query}%`] : [];
    const completeParams = `pageSize=20&search=${searches.join(';')}`;

    this.productService.findAllDTO(completeParams).subscribe({
      next: (page) => {
        this.products.values = page.content;
      },
      error: () => {
        this.products.values = [];
      },
    });
  }

  searchTickets(event: any): void {
    this.tickets.isLoading = true;

    const query = event.query;

    const search = query ? `&search=number==${query}` : '';
    const completeParams = `pageSize=100${search}`;

    this.ticketService.findAllDTO(completeParams).subscribe({
      next: (page) => (this.tickets.values = page.content),
      error: () => (this.tickets.values = []),
      complete: () => {
        this.tickets.isLoading = false;
        this.cdr.markForCheck();
      },
    });
  }

  onCancel(): void {

    if (this.isEditMode) {
      return;
    }

    this.orderService.delete(this.orderGroupId().order)
      .subscribe(() => this.navigateToList());
  }

  onSave(activateCallback: () => void): void {
    if (this.form.invalid) return;

    const payload = this.form.getRawValue();

    if (this.isEditMode) {
      this.update(payload, activateCallback);
      return;
    }

    this.create(payload, activateCallback);
  }

  private create(payload: any, activateCallback: () => void): void {
    this.orderService.create(payload).subscribe({
      next: () => this.next(activateCallback),
      error: (error) => console.error('Erro ao criar pedido:', error),
    });
  }

  private update(payload: any, activateCallback: () => void): void {
    this.orderService.update(this.id, payload).subscribe({
      next: () => this.next(activateCallback),
      error: (error) => console.error('Erro ao atualizar pedido:', error),
    });
  }

  navigateToList(): void {
    this.router.navigate(['/order']);
  }

  prev(activateCallback: () => void) {
    this.stepIndex--;
    activateCallback();
  }

  next(activateCallback: () => void) {
    this.stepIndex++;
    activateCallback();
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

  protected setProductInGroup(id: string) {
    this.orderGroupId.set({
      ...this.orderGroupId(),
      product: id
    });
  }

  protected setItemInGroup(id: string) {
    this.orderGroupId.set({
      ...this.orderGroupId(),
      item: id
    });
  }

}
