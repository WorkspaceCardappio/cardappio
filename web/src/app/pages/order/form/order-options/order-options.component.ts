import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  signal,
} from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ProductService } from '../../../product/product.service';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ButtonModule } from 'primeng/button';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectButtonModule } from 'primeng/selectbutton';
import { StepperModule } from 'primeng/stepper';
import { TableModule } from 'primeng/table';
import { combineLatest, startWith } from 'rxjs';
import { Loader } from '../../../../model/loader';
import FormatterUtils from '../../../../utils/formatter.utils';
import { AdditionalService } from '../../../additional/additional.service';

@Component({
  selector: 'app-order-options',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    AutoCompleteModule,
    InputNumberModule,
    ButtonModule,
    TableModule,
    StepperModule,
    SelectButtonModule,
    FormsModule,
    CommonModule,
    FloatLabelModule,
  ],
  providers: [ProductService],
  templateUrl: './order-options.component.html',
  styleUrls: ['./order-options.component.scss'],
})
export class OrderOptionsComponent implements OnInit {
  @Input({ required: true }) orderId!: string;
  @Output() prevEmitter: EventEmitter<void> = new EventEmitter();
  @Output() nextEmitter: EventEmitter<void> = new EventEmitter();

  selectedProductType: any = null;

  stepIndex = 1;

  form: FormGroup = new FormGroup({});

  products: Loader = { values: [] };
  options: Loader = { values: [] };

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
    private readonly additionalService: AdditionalService,
    private readonly productService: ProductService,
    private readonly builder: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.form = this.initForm();
  }

  private initForm() {
    const form = this.builder.group({
      id: [''],
      order: [this.orderId],
      product: [null],
      item: [null, Validators.required],
      quantity: [
        1,
        Validators.compose([Validators.required, Validators.min(1)]),
      ],
      price: [0, Validators.min(0.001)],
      total: [0],
    });

    form.get('product')?.valueChanges
      .subscribe((product: any) => {

        if(product?.id)
          this.productService.findOptionsById(product?.id)
          .subscribe((options: any) => {
            this.options.values = options;

            if (!options.length) {
              this.form.get('item')?.setValue(null);
              this.form.get('price')?.setValue(0);
              return;
            }


            if (options.length === 1) {
              form.get('item')?.setValue(options[0]);
            }

            this.cdr.markForCheck();
          });

      });

    form.get('item')?.valueChanges
      .subscribe((item: any) => {

        if (item?.price)
          form.get('price')?.setValue(item.price);

      });

    this.setupTotalCalculation(form);

    return form;
  }

  searchProducts(event: any): void {
    const query = event.query || '';
    const searches = query ? [`name=ilike=${query}%`] : [];
    const completeParams = `pageSize=20&search=${searches.join(';')}`;

    this.productService.findAllDTO(completeParams).subscribe({
      next: (page) => (this.products.values = page.content),
      error: () => (this.products.values = []),
      complete: () => this.cdr.markForCheck(),
    });
  }

  private setupTotalCalculation(form: FormGroup<any>) {
    const quantityForm = form.get('quantity')!.valueChanges.pipe(startWith(form.get('quantity')!.value));
    const priceForm = form.get('price')!.valueChanges.pipe(startWith(form.get('price')!.value));

    combineLatest([quantityForm, priceForm])
      .subscribe(([quantity, price]) => {
        form.get('quantity')!.valid && form.get('price')!.valid
          ? form.get('total')!.setValue(quantity * price)
          : form.get('total')!.setValue(0);
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
    product.opcoes = product.opcoes.map((item: any) => ({
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
    this.additionalService
      .findByProductIdToOrder(productId)
      .subscribe((values: any) => (this.initialAdditionals = values));
  }

  private findOptions(productId: string) {
    // TODO: Buscar opções do produto

    this.additionalService
      .findByProductIdToOrder(productId)
      .subscribe((values: any) => (this.initialAdditionals = values));
  }
}
