import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {
  AutocompleteComponent,
  CancelButtonComponent,
  SaveButtonComponent
} from "cardappio-component-hub";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { Observable } from "rxjs";
import { OrderService } from "../order.service";
import { TicketService } from "../../ticket.service";
import { ProductService } from "../../product.service";

@Component({
  selector: 'app-order-form',
  imports: [
    CancelButtonComponent,
    ReactiveFormsModule,
    SaveButtonComponent,
    AutocompleteComponent
  ],
  templateUrl: './order-form.component.html',
  styleUrl: './order-form.component.scss'
})
export class OrderFormComponent implements OnInit {

  orderId: string | null = null;
  isEditMode = false;
  orderForm: FormGroup;

  constructor(
    private readonly orderService: OrderService,
    private readonly ticketService: TicketService,
    private readonly productService: ProductService,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly cdr: ChangeDetectorRef,
    private readonly fb: FormBuilder
  ) {
    this.orderForm = this.fb.group({
      id: [''],
      products: [[], Validators.required],
      ticket: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.initializeComponent();
  }

  ticketsSrc = (query: string): Observable<any[]> => {
    const searchs = [];

    if (query) {
      searchs.push(`number=ilike=${query}%`);
    }

    const id = this.orderForm.get('id')?.value;
    if (id) {
      searchs.push(`id=out=${id}`);
    }

    return this.ticketService.findAll(20, searchs.join(';'));
  }

  productsSrc = (query: string): Observable<any[]> => {
    const searchs = [];

    if (query) {
      searchs.push(`name=ilike=${query}%`);
    }

    const id = this.orderForm.get('id')?.value;
    if (id) {
      searchs.push(`id=out=${id}`);
    }

    return this.productService.findAll(20, searchs.join(';'));
  }

  displayProduct = (item: any) => {
    return item.name;
  }

  displayTicket = (item: any) => {
    return item.number;
  }

  onCancel(): void {
    this.navigateToList();
  }

  onSave(): void {
    if (this.orderForm.invalid) return;

    if (this.isEditMode) {
      this.updateOrder();
    } else {
      this.createOrder();
    }
  }

  private initializeComponent(): void {
    this.orderId = this.route.snapshot.paramMap.get('id');
    this.isEditMode = !!this.orderId;

    if (this.isEditMode) {
      this.loadOrder();
    }
  }

  private loadOrder(): void {
    this.orderService.findById(this.orderId).subscribe({
      next: (order) => {
        this.orderForm.patchValue({
          id: order.id,
          products: order.products || [],
          ticket: order.ticket || null
        });
        this.cdr.detectChanges();
      },
      error: () => this.navigateToList()
    });
  }

  private createOrder(): void {
    const { id, ...orderData } = this.orderForm.value;

    this.orderService.create(orderData).subscribe({
      next: () => this.navigateToList(),
      error: (error) => console.error('Erro ao criar pedido:', error)
    });
  }

  private updateOrder(): void {
    this.orderService.update(this.orderId, this.orderForm.value).subscribe({
      next: () => this.navigateToList(),
      error: (error) => console.error('Erro ao atualizar pedido:', error)
    });
  }

  protected navigateToList(): void {
    this.router.navigate(['/order']);
  }
}
