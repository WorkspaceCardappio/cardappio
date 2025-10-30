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
import { VariableService } from '../../../variable/variable.service';
import { OrderVariableService } from './service/order-variable.service';

@Component({
  selector: 'app-order-variable',
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
  templateUrl: './order-variable.component.html',
  styleUrls: ['./order-variable.component.scss'],
})
export class OrderVariableComponent implements OnInit {

  @Input({ required: true }) productId!: string;
  @Input({ required: true }) itemId!: string;
  @Output() prevEmitter: EventEmitter<void> = new EventEmitter();
  @Output() nextEmitter: EventEmitter<void> = new EventEmitter();

  form: FormArray<FormGroup<any>> = new FormArray<FormGroup<any>>([]);

  initialVariables = [];
  selectedVariables: any[] = [];

  constructor(
    private readonly service: VariableService,
    private readonly builder: FormBuilder,
    private orderVariableService: OrderVariableService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.findVariables(this.productId);
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
        formArray.push(this.createVariableFormGroup(item.id, item.price))
      );

    this.selectedVariables = selected;
  }

  createVariableFormGroup(id: number, price: number): FormGroup {
    const form = this.builder.group({
      id: [''],
      variable: [id],
      price: [price],
      order: [this.itemId],
      item: [null, Validators.required],
      quantity: [
        1,
        Validators.compose([Validators.required, Validators.min(1)]),
      ],
      total: [price],
    });

    form.get('quantity')?.valueChanges.subscribe((quantity: any) => {
      const price = form.get('price')?.value;

      if (price && quantity)
        form.get('total')?.setValue(price * quantity);
    });

    return form;
  }

  getFormGroupByVariableId(id: number): FormGroup {
    return (
      (this.form.controls.find(
        (value) => value.get('variable')?.value === id
      ) as FormGroup) || this.createVariableFormGroup(0, 0)
    );
  }

  isVariableSelected(id: number): boolean {
    return this.selectedVariables.some((v) => v.id === id);
  }

  onSave() {

    if (!this.form.value.length) {
      this.nextEmitter.emit();
      return;
    }

    this.orderVariableService.createItems(this.form.getRawValue())
      .subscribe(() => this.nextEmitter.emit());
  }

  getTotal() {
    return this.form.value
      .map(item => item.total)
      .reduce((acc, current) => acc + current, 0);
  }

  private findVariables(productId: string) {
    this.service
      .findByProductIdToOrder(productId)
      .subscribe((values: any) => {
        this.initialVariables = values;
        this.cdr.markForCheck();
      });
  }
}
