import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ProductService } from '../../../product/product.service';

import { CommonModule } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
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
import { OrderOptionsService } from './service/order-options.service';

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
  providers: [ProductService, OrderOptionsService],
  templateUrl: './order-options.component.html',
  styleUrls: ['./order-options.component.scss'],
})
export class OrderOptionsComponent implements OnInit {
  @Input({ required: true }) orderId!: string;
  @Output() prevEmitter: EventEmitter<void> = new EventEmitter();
  @Output() nextEmitter: EventEmitter<void> = new EventEmitter();
  @Output() onProductSelect: EventEmitter<string> = new EventEmitter();
  @Output() onItemSelect: EventEmitter<string> = new EventEmitter();

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

  constructor(
    private readonly service: OrderOptionsService,
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

    form.get('product')?.valueChanges.subscribe((product: any) => {
      if (!product?.id) return;

      this.onProductSelect.emit(product.id);

      this.productService
        .findOptionsById(product?.id)
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

    form.get('item')?.valueChanges.subscribe((item: any) => {
      if (item?.price) form.get('price')?.setValue(item.price);
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
    const quantityForm = form
      .get('quantity')!
      .valueChanges.pipe(startWith(form.get('quantity')!.value));
    const priceForm = form
      .get('price')!
      .valueChanges.pipe(startWith(form.get('price')!.value));

    combineLatest([quantityForm, priceForm]).subscribe(([quantity, price]) => {
      form.get('quantity')!.valid && form.get('price')!.valid
        ? form.get('total')!.setValue(quantity * price)
        : form.get('total')!.setValue(0);
    });
  }

  onSave() {
    this.service
      .create(this.form.getRawValue())
      .subscribe((response: HttpResponse<any>) => {
        this.onItemSelect.emit(response.body);
        this.nextEmitter.emit()
      });
  }
}
