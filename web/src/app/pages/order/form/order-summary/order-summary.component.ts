import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
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
import { Router } from '@angular/router';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectButtonModule } from 'primeng/selectbutton';
import { SkeletonModule } from 'primeng/skeleton';
import { TableModule } from 'primeng/table';
import { OrderService } from '../../service/order.service';

@Component({
  selector: 'app-order-summary',
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
    SkeletonModule,
    DialogModule,
  ],
  providers: [ProductService, OrderService],
  templateUrl: './order-summary.component.html',
  styleUrls: ['./order-summary.component.scss'],
})
export class OrderSummaryComponent implements OnInit {
  @Input({ required: true }) orderId!: string;
  @Input({ required: true }) itemId!: string;
  @Output() prevEmitter: EventEmitter<void> = new EventEmitter();
  @Output() nextEmitter: EventEmitter<void> = new EventEmitter();

  form: FormArray<FormGroup<any>> = new FormArray<FormGroup<any>>([]);

  initialVariables = [];
  selectedVariables: any[] = [];

  order: any = null;
  orderSummary: any[] = [];

  protected orderNoteForm: FormGroup<any> = new FormGroup({});
  protected noteForm: FormGroup<any> = new FormGroup({});

  showNoteDialog = false;
  showOrderNoteDialog = false;

  selectedProduct: any = null;

  constructor(
    private readonly orderService: OrderService,
    private readonly builder: FormBuilder,
    private cdr: ChangeDetectorRef,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.orderNoteForm = this.buildNoteForm();
    this.noteForm = this.buildNoteForm();

    this.orderService.findById(this.orderId).subscribe({
      next: (value) => {
        this.order = value;
        this.cdr.markForCheck();
      },
    });

    this.findToSummary();
  }

  private findToSummary() {
    this.orderService.findToSummary(this.orderId).subscribe({
      next: (value) => {
        this.orderSummary = value;
        this.cdr.markForCheck();
      },
    });
  }

  saveNote() {
    const noteValue = this.noteForm.get('note')?.value;
    const productId = this.noteForm.get('id')?.value;

    this.orderService
      .updateNoteInProductOrder(
        this.noteForm.get('id')?.value,
        this.noteForm.getRawValue()
      )
      .subscribe({
        next: () => {
          const index = this.orderSummary.findIndex((p) => p.id === productId);
          if (index !== -1) {
            this.orderSummary[index].note = noteValue;
          }
        },
        complete: () => {
          this.selectedProduct = null;
          this.showNoteDialog = false;
          this.cdr.markForCheck();
        },
      });
  }

  openObservationDialog(product: any) {
    this.selectedProduct = product;

    this.noteForm.setValue({
      id: product.id,
      note: product.note || '',
    });

    this.showNoteDialog = true;
  }

  getTotalGeneral(): number {
    return this.orderSummary.reduce(
      (acc, item) =>
        acc + item.price + item.additionalsPrice + item.variablesPrice,
      0
    );
  }

  openOrderNoteDialog() {
    this.orderNoteForm.setValue({
      id: this.order.id,
      note: this.order?.note || '',
    });
    this.showOrderNoteDialog = true;
  }

  saveOrderNote() {
    this.orderService
      .updateNoteInOrder(this.order.id, this.orderNoteForm.getRawValue())
      .subscribe({
        next: () => {
          this.order.note = this.orderNoteForm.get('note')?.value;
        },
        complete: () => {
          this.showOrderNoteDialog = false;
          this.cdr.markForCheck();
        },
      });
  }

  deleteItem(id: string) {
    this.orderService.deleteProductInOrder(id).subscribe({
      next: () => this.findToSummary(),
      complete: () => this.cdr.markForCheck(),
    });
  }

  navigateToList(): void {
    this.router.navigate(['/order']);
  }

  onSave() {

    this.orderService.finalize(this.orderId)
      .subscribe({
        next: () => this.navigateToList()
      });
  }

  private buildNoteForm() {
    return this.builder.group({
      id: [null, Validators.required],
      note: [null, [Validators.required, Validators.maxLength(255)]],
    });
  }
}
