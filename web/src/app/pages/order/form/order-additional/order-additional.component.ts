import { ChangeDetectorRef, Component, Input, OnInit, signal } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../../product/product.service';
import { OrderService } from '../../service/order.service';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ButtonModule } from 'primeng/button';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectButtonModule } from 'primeng/selectbutton';
import { TableModule } from 'primeng/table';
import { Loader } from '../../../../model/loader';
import FormatterUtils from '../../../../utils/formatter.utils';
import { AdditionalService } from '../../../additional/additional.service';

@Component({
  selector: 'app-order-additional',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    AutoCompleteModule,
    InputNumberModule,
    ButtonModule,
    TableModule,
    SelectButtonModule,
    FormsModule,
    CommonModule,
    FloatLabelModule,
  ],
  providers: [
    ProductService
  ],
  templateUrl: './order-additional.component.html',
  styleUrls: ['./order-additional.component.scss'],
})
export class OrderAdditionalComponent implements OnInit {

  @Input({ required: true }) orderId!: string;
  @Input({ required: true }) activateCallback!: () => void;


  selectedProductType: any = null;

  stepIndex = 1;

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

  form: FormGroup = new FormGroup({});

  products: Loader = { values: [] };

  quantity = 1;

  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [
    { label: 'Pedidos', routerLink: '/order' },
    { label: 'Novo', routerLink: '/order/new' },
  ];

  priceTotal = signal(0);

  initialAdditionals = [];
  selectedAdditionals: any[] = [];

  constructor(
    private readonly orderService: OrderService,
    private readonly additionalService: AdditionalService,
    private readonly productService: ProductService,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly builder: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.form = this.initForm();
  }

  private initForm() {
    const form = this.builder.group({
      id: [''],
      total: [0, [Validators.required, Validators.min(0)]],
      status: [null, Validators.required],
      product: [null],
      products: this.builder.array([]),
      ticket: [null, Validators.required],
    });

    form.get('product')
      ?.valueChanges
      .subscribe((product: any) => {

        if (product?.id) return;

        this.findAdditionals(product.id);
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

  // onSave(): void {

  //   if (this.form.invalid) return;

  //   const { total, orderStatus, products, ticket } = this.form.value;

  //   const orderPayload = {
  //     total: total || 0,
  //     orderStatus: orderStatus || 'PENDING',
  //     ticketId: ticket?.id,
  //     products: this.formatProducts(products),
  //   };

  //   if (this.isEditMode) {
  //     this.updateOrder(orderPayload);
  //   } else {
  //     this.createOrder(orderPayload);
  //   }
  // }

  // private formatProducts(products: any[]): any[] {
  //   if (!products || !Array.isArray(products)) {
  //     return [];
  //   }

  //   return products.map((product) => ({
  //     productId: product.id,
  //     quantity: 1,
  //   }));
  // }

  // private createOrder(payload: any): void {
  //   this.orderService.create(payload).subscribe({
  //     next: () => this.navigateToList(),
  //     error: (error) => console.error('Erro ao criar pedido:', error),
  //   });
  // }

  // private updateOrder(payload: any): void {
  //   if (!this.id) return;

  //   this.orderService.update(this.id, payload).subscribe({
  //     next: () => this.navigateToList(),
  //     error: (error) => console.error('Erro ao atualizar pedido:', error),
  //   });
  // }

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

    this.selectedAdditionals = selected;
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
    return this.selectedAdditionals.some((p) => p.id === id);
  }

  get additionals() {
    return this.form.get('additionals') as FormArray;
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

  private findAdditionals(productId: string) {

    this.additionalService.findByProductIdToOrder(productId)
      .subscribe((values: any) => this.initialAdditionals = values);

  }

  prev() {
    this.stepIndex--;
    this.activateCallback();
  }

  next() {
    this.stepIndex++;
    this.activateCallback();
  }

  private findOptions(productId: string) {

    // TODO: Buscar opções do produto

    this.additionalService.findByProductIdToOrder(productId)
      .subscribe((values: any) => this.initialAdditionals = values);

  }
}
