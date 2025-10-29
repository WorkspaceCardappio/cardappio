import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output
} from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ProductService } from '../../../product/service/product.service';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ButtonModule } from 'primeng/button';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectButtonModule } from 'primeng/selectbutton';
import { TableModule } from 'primeng/table';
import FormatterUtils from '../../../../utils/formatter.utils';
import { AdditionalService } from '../../../additional/additional.service';
import { OrderAdditionalService } from './service/order-additional.service';

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
    FloatLabelModule
  ],
  providers: [ProductService],
  templateUrl: './order-additional.component.html',
  styleUrls: ['./order-additional.component.scss'],
})
export class OrderAdditionalComponent implements OnInit {
  @Input({ required: true }) productId!: string;
  @Input({ required: true }) itemId!: string;
  @Output() prevEmitter: EventEmitter<void> = new EventEmitter();
  @Output() nextEmitter: EventEmitter<void> = new EventEmitter();

  form: FormArray<FormGroup<any>> = new FormArray<FormGroup<any>>([]);

  initialAdditionals = [];
  selectedAdditionals: any[] = [];

  constructor(
    private readonly additionalService: AdditionalService,
    private readonly builder: FormBuilder,
    private orderAdditionalService: OrderAdditionalService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.findAdditionals(this.productId);
  }

  onSelectionChange(selected: any[]) {
    const formArray = this.form;
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
      id: [''],
      productId: [id],
      order: [this.itemId],
      item: [null, Validators.required],
      quantity: [
        1,
        Validators.compose([Validators.required, Validators.min(1)]),
      ],
      total: [0],
    });

    form.get('item')?.valueChanges.subscribe((item: any) => {
      const quantity = form.get('quantity')?.value;

      if (item?.price && quantity)
        form.get('total')?.setValue(item.price * quantity);
    });

    form.get('quantity')?.valueChanges.subscribe((quantity: any) => {
      const item = form.get('item')?.value as any;

      if (item?.price && quantity)
        form.get('total')?.setValue(item.price * quantity);
    });

    return form;
  }

  getFormGroupByProductId(id: number): FormGroup {
    return (
      (this.form.controls.find(
        (value) => value.get('productId')?.value === id
      ) as FormGroup) || this.createAdicionalFormGroup(0)
    );
  }

  isProductSelected(id: number): boolean {
    return this.selectedAdditionals.some((p) => p.id === id);
  }

  filterItems(product: any) {
    product.items = product.items.map((item: any) => ({
      ...item,
      label: this.buildOptionLabel(item),
    }));
  }

  buildOptionLabel(item: any) {
    const price = FormatterUtils.price(item.price);
    return `${item.name} - ${price}`;
  }

  onSave() {

    if (!this.form.value.length) {
      this.nextEmitter.emit();
      return;
    }

    this.orderAdditionalService.createItems(this.form.getRawValue())
      .subscribe(() => this.nextEmitter.emit());
  }

  getTotal() {
    return this.form.value
      .map(item => item.total)
      .reduce((acc, current) => acc + current, 0);
  }

  private findAdditionals(productId: string) {
    this.additionalService
      .findByProductIdToOrder(productId)
      .subscribe((values: any) => {
        this.initialAdditionals = values;
        this.cdr.markForCheck();
      });
  }
}
