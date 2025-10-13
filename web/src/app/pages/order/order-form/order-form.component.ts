import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {
  AutocompleteComponent,
  CancelButtonComponent,
  SaveButtonComponent
} from "cardappio-component-hub";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { forkJoin, Observable, of } from "rxjs";
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
  initialTicket: any = null;

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
      price: [0, [Validators.required, Validators.min(0)]],
      orderStatus: ['PENDING', Validators.required],
      products: [[], Validators.required],
      ticket: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.orderId = this.route.snapshot.paramMap.get('id');
    this.isEditMode = !!this.orderId;

    if (this.isEditMode) {
      this.loadOrder();
    }
  }

  ticketsSrc = (query: string): Observable<any[]> => {
    const searches = query ? [`number=ilike=${query}%`] : [];
    return this.ticketService.findAll(20, searches.join(';'));
  }

  productsSrc = (query: string): Observable<any[]> => {
    const searches = query ? [`name=ilike=${query}%`] : [];
    return this.productService.findAll(20, searches.join(';'));
  }

  displayProduct = (item: any): string => item?.name || '';

  displayTicket = (item: any): string => {
    return item?.number?.toString() || '';
  }

  onCancel(): void {
    this.navigateToList();
  }

  onSave(): void {
    if (this.orderForm.invalid) {
      console.error('Formulário inválido. Verifique os campos obrigatórios.');
      return;
    }

    const { price, orderStatus, products, ticket } = this.orderForm.value;

    const orderPayload = {
      price: price || 0,
      orderStatus: orderStatus || 'PENDING',
      ticketId: ticket.id,
      products: this.formatProducts(products)
    };

    if (this.isEditMode) {
      this.updateOrder(orderPayload);
    } else {
      this.createOrder(orderPayload);
    }
  }

  private formatProducts(products: any[]): any[] {
    if (!products || !Array.isArray(products)) {
      return [];
    }

    return products.map(product => ({
      product: { id: product.id }
    }));
  }

  private loadOrder(): void {
    if (!this.orderId) return;

    this.orderService.findById(this.orderId).subscribe({
      next: (order) => {
        if (order.ticketId) {
          forkJoin({
            order: of(order),
            ticket: this.ticketService.findById(order.ticketId)
          }).subscribe({
            next: ({ order, ticket }) => {
              console.log('Ticket carregado:', ticket);

              this.initialTicket = ticket;

              this.orderForm.patchValue({
                id: order.id,
                price: order.price || 0,
                orderStatus: order.orderStatus || 'PENDING',
                products: order.products || [],
                ticket: ticket
              });

              this.cdr.detectChanges();

              console.log('Initial ticket setado:', this.initialTicket);
              console.log('Display deve mostrar:', this.displayTicket(ticket));
            },
            error: (error) => {
              console.error('Erro ao carregar dados:', error);
              this.navigateToList();
            }
          });
        } else {
          this.orderForm.patchValue({
            id: order.id,
            price: order.price || 0,
            orderStatus: order.orderStatus || 'PENDING',
            products: order.products || []
          });
          this.cdr.detectChanges();
        }
      },
      error: (error) => {
        console.error('Erro ao carregar pedido:', error);
        this.navigateToList();
      }
    });
  }

  private createOrder(payload: any): void {
    this.orderService.create(payload).subscribe({
      next: () => {
        this.navigateToList();
      },
      error: (error) => console.error('Erro ao criar pedido:', error)
    });
  }

  private updateOrder(payload: any): void {
    if (!this.orderId) return;

    this.orderService.update(this.orderId, payload).subscribe({
      next: () => {
        this.navigateToList();
      },
      error: (error) => console.error('Erro ao atualizar pedido:', error)
    });
  }

  navigateToList(): void {
    this.router.navigate(['/order']);
  }
}
