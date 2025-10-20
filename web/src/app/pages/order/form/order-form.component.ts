import { ChangeDetectorRef, Component, OnInit, signal } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { forkJoin, of, switchMap } from 'rxjs';
import { ProductService } from '../../product/product.service';
import { TicketService } from '../../ticket/service/ticket.service';
import { OrderService } from '../service/order.service';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { Breadcrumb } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { Fieldset } from 'primeng/fieldset';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectButtonModule } from 'primeng/selectbutton';
import { StepperModule } from 'primeng/stepper';
import { TableModule } from 'primeng/table';
import { Loader } from '../../../model/loader';
import FormatterUtils from '../../../utils/formatter.utils';

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
  ],
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.scss'],
})
export class OrderFormComponent implements OnInit {
  selectedProductType: any = null;

  productTypeOptions = [
    {
      label: 'Único',
      value: 'unico',
      price: 10.0,
      description: 'Produto padrão, sem adicionais.',
      disabled: false,
    },
    {
      label: 'Pequeno',
      value: 'pequeno',
      price: 8.0,
      description: 'Tamanho pequeno (250ml ou menos).',
      disabled: false,
    },
    {
      label: 'Médio',
      value: 'medio',
      price: 11.0,
      description: 'tamanho médio',
      disabled: false,
    },
    {
      label: 'Grande',
      value: 'grande',
      price: 14.0,
      description: 'Tamanho grande (500ml ou mais).',
      disabled: true,
    },
  ];

  orderId: string | null = null;
  isEditMode = false;

  form: FormGroup = new FormGroup({});
  formProductItem: FormGroup = new FormGroup({});

  tickets: Loader = { values: [] };
  products: Loader = { values: [] };

  quantity = 1;

  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [
    { label: 'Pedidos', routerLink: '/order' },
    { label: 'Novo', routerLink: '/order/new' },
  ];

  priceTotal = signal(0);

  constructor(
    private readonly orderService: OrderService,
    private readonly ticketService: TicketService,
    private readonly productService: ProductService,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly builder: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.form = this.initForm();
    this.formProductItem = this.buildProductItemForm();

    this.orderId = this.route.snapshot.paramMap.get('id');
    this.isEditMode = !!this.orderId;

    if (this.isEditMode) {
      this.loadOrder();
    }
  }

  private initForm() {
    return this.builder.group({
      id: [''],
      total: [0, [Validators.required, Validators.min(0)]],
      status: [null, Validators.required],
      product: [null],
      products: this.builder.array([]),
      ticket: [null, Validators.required],
    });
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
    this.navigateToList();
  }

  onSave(): void {
    if (this.form.invalid) return;

    const { total, orderStatus, products, ticket } = this.form.value;

    const orderPayload = {
      total: total || 0,
      orderStatus: orderStatus || 'PENDING',
      ticketId: ticket?.id,
      products: this.formatProducts(products),
    };

    if (this.isEditMode && this.orderId) {
      this.updateOrder(orderPayload);
    } else {
      this.createOrder(orderPayload);
    }
  }

  private formatProducts(products: any[]): any[] {
    if (!products || !Array.isArray(products)) {
      return [];
    }

    return products.map((product) => ({
      productId: product.id,
      quantity: 1,
    }));
  }

  private loadOrder(): void {
    if (!this.orderId) return;

    this.orderService
      .findById(this.orderId)
      .pipe(
        switchMap((order) => {
          const ticket$ = order.ticketId
            ? this.ticketService.findById(order.ticketId)
            : of(null);

          const productIds = order.products.map((p: any) => p.productId);
          const products$ = this.productService.findAllDTO(productIds);

          return forkJoin({
            order: of(order),
            ticket: ticket$,
            products: products$,
          });
        })
      )
      .subscribe(({ order, ticket, products }) => {
        this.form.patchValue({
          total: order.total,
          ticket: ticket,
          products: products.content,
        });
      });
  }

  private createOrder(payload: any): void {
    this.orderService.create(payload).subscribe({
      next: () => this.navigateToList(),
      error: (error) => console.error('Erro ao criar pedido:', error),
    });
  }

  private updateOrder(payload: any): void {
    if (!this.orderId) return;

    this.orderService.update(this.orderId, payload).subscribe({
      next: () => this.navigateToList(),
      error: (error) => console.error('Erro ao atualizar pedido:', error),
    });
  }

  navigateToList(): void {
    this.router.navigate(['/order']);
  }

  isOptionDisabled = (option: any) => !option.active;

  private buildProductItemForm() {
    const form = this.builder.group({
      id: [null],
      item: [null, Validators.required],
      quantity: [
        1,
        Validators.compose([Validators.required, Validators.min(1)]),
      ],
      variables: this.builder.array([]),
      additionals: this.builder.array([]),
      note: [null],
    });

    form.get('item')?.valueChanges.subscribe((item: any) => {
      const quantity = form.get('quantity')?.value;

      if (item?.price && quantity) {
        this.priceTotal.set(item.price * quantity);
      }
    });

    form.get('quantity')?.valueChanges.subscribe((quantity: any) => {
      const item = form.get('item')?.value as any;

      if (item?.price && quantity) {
        this.priceTotal.set(item.price * quantity);
      }
    });

    return form;
  }

  protected adicionaisIniciais = [
    {
      id: 1,
      nome: 'Extra queijo',
      opcoes: [
        { id: 101, tamanho: 'pequeno', preco: 2.0 },
        { id: 102, tamanho: 'médio', preco: 3.0 },
        { id: 103, tamanho: 'grande', preco: 4.0 },
      ],
      category: 12,
      quantity: 1,
    },
    {
      id: 2,
      nome: 'Bacon',
      opcoes: [
        { id: 201, tamanho: 'fatias', preco: 3.5 },
        { id: 202, tamanho: 'crocante', preco: 4.0 },
      ],
      category: 12,
      quantity: 1,
    },
    {
      id: 3,
      nome: 'Molho especial',
      opcoes: [
        { id: 301, tamanho: 'picante', preco: 1.5 },
        { id: 302, tamanho: 'doce', preco: 1.8 },
        { id: 303, tamanho: 'barbecue', preco: 2.0 },
      ],
      category: 13,
      quantity: 1,
    },
  ];

  selectedAdicionais: any[] = [];

  onSelectionChange(selected: any[]) {
    const formArray = this.additionals;
    const selectedIds = new Set(selected.map((s) => s.id));
    const currentIds = new Set(formArray.controls.map((item) => item.value.id));

    formArray.controls
      .filter((ctrl) => !selectedIds.has(ctrl.value.id))
      .forEach((ctrl) => formArray.removeAt(formArray.controls.indexOf(ctrl)));

    selected
      .filter((item) => !currentIds.has(item.id))
      .forEach((item) =>
        formArray.push(this.createAdicionalFormGroup(item.id))
      );

    this.selectedAdicionais = selected;
  }

  createAdicionalFormGroup(id: number): FormGroup {
    const form = this.builder.group({
      id: [id],
      opcao: [null, Validators.required],
      quantity: [
        1,
        Validators.compose([Validators.required, Validators.min(1)]),
      ],
    });

    return form;
  }

  getFormGroupByProductId(id: number): FormGroup {
    return (
      (this.additionals.controls.find(
        (value) => value.get('id')?.value === id
      ) as FormGroup) || this.createAdicionalFormGroup(0)
    );
  }

  isProductSelected(id: number): boolean {
    return this.selectedAdicionais.some((p) => p.id === id);
  }

  get additionals() {
    return this.formProductItem.get('additionals') as FormArray;
  }

  filterOpcoes(product: any) {

    product.opcoes = product.opcoes
      .map((item: any) => ({
        ...item,
        label: this.buildOptionLabel(item),
      }));
  }

  getSelectedItemName(item: any): string {
    return `${item.id} - ${item.name}`;
  }

  buildOptionLabel(item: any) {
    const price = FormatterUtils.price(item.preco);
    return `${item.tamanho} - ${price}`;
  }
}
