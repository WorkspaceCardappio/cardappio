import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { forkJoin, of } from "rxjs";
import { OrderService } from "../service/order.service";
import { ProductService } from "../../product/product.service";
import { TicketService } from "../../ticket/service/ticket.service";

import { AutoCompleteModule } from 'primeng/autocomplete';
import { InputNumberModule } from 'primeng/inputnumber';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-order-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    AutoCompleteModule,
    InputNumberModule,
    ButtonModule
  ],
  templateUrl: './order-form.component.html',
  styleUrl: './order-form.component.scss'
})
export class OrderFormComponent implements OnInit {

  orderId: string | null = null;
  isEditMode = false;
  orderForm: FormGroup;

  filteredProducts: any[] = [];
  filteredTickets: any[] = [];

  constructor(
    private readonly orderService: OrderService,
    private readonly ticketService: TicketService,
    private readonly productService: ProductService,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
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

  searchProducts(event: any): void {
    const query = event.query || '';
    const searches = query ? [`name=ilike=${query}%`] : [];

    this.productService.findAll(20, searches.join(';')).subscribe({
      next: (products) => {
        this.filteredProducts = products;
      },
      error: () => {
        this.filteredProducts = [];
      }
    });
  }

  searchTickets(event: any): void {
    const query = event.query || '';
    const searches = query ? [`number=ilike=${query}%`] : [];

    this.ticketService.findAll(20, searches.join(';')).subscribe({
      next: (tickets) => {
        this.filteredTickets = tickets;
      },
      error: () => {
        this.filteredTickets = [];
      }
    });
  }

  onCancel(): void {
    this.navigateToList();
  }

  onSave(): void {
    if (this.orderForm.invalid) {
      console.log('Formulário inválido');
      return;
    }

    const { price, orderStatus, products, ticket } = this.orderForm.value;

    const orderPayload = {
      price: price || 0,
      orderStatus: orderStatus || 'PENDING',
      ticketId: ticket?.id,
      products: this.formatProducts(products)
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
              this.orderForm.patchValue({
                id: order.id,
                price: order.price || 0,
                orderStatus: order.orderStatus || 'PENDING',
                products: order.products || [],
                ticket: ticket
              });
            },
            error: () => {
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
        }
      },
      error: () => {
        this.navigateToList();
      }
    });
  }

  private createOrder(payload: any): void {
    this.orderService.create(payload).subscribe({
      next: () => {
        this.navigateToList();
      },
      error: (error) => {
        console.error('Erro ao criar pedido:', error);
      }
    });
  }

  private updateOrder(payload: any): void {
    if (!this.orderId) return;

    this.orderService.update(this.orderId, payload).subscribe({
      next: () => {
        this.navigateToList();
      },
      error: (error) => {
        console.error('Erro ao atualizar pedido:', error);
      }
    });
  }

  navigateToList(): void {
    this.router.navigate(['/order']);
  }
}
