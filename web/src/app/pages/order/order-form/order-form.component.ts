import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { forkJoin, of, switchMap } from "rxjs";
import { OrderService } from "../service/order.service";
import { ProductService } from "../../product/product.service";
import { TicketService } from "../../ticket/service/ticket.service";

import { AutoCompleteModule } from 'primeng/autocomplete';
import { InputNumberModule } from 'primeng/inputnumber';
import { ButtonModule } from 'primeng/button';
import { Fieldset } from "primeng/fieldset";
import { Breadcrumb } from "primeng/breadcrumb";

@Component({
  selector: 'app-order-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    AutoCompleteModule,
    InputNumberModule,
    ButtonModule,
    Fieldset,
    Breadcrumb
  ],
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.scss']
})
export class OrderFormComponent implements OnInit {

  orderId: string | null = null;
  isEditMode = false;
  orderForm: FormGroup;

  filteredProducts: any[] = [];
  filteredTickets: any[] = [];


  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [
    { label: 'Pedidos', routerLink: '/order' },
    { label: 'Novo', routerLink: '/order/new' },
  ];


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
      total: [0, [Validators.required, Validators.min(0)]],
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
    const completeParams = `pageSize=20&search=${searches.join(';')}`;

    this.productService.findAllDTO(completeParams).subscribe({
      next: (page) => {
        this.filteredProducts = page.content;
      },
      error: () => {
        this.filteredProducts = [];
      }
    });
  }

  searchTickets(event: any): void {
    const query = event.query || '';
    const searches = query ? [`number=ilike=${query}%`] : [];
    const completeParams = `pageSize=20&search=${searches.join(';')}`;

    this.ticketService.findAllDTO(completeParams).subscribe({
      next: (page) => {
        this.filteredTickets = page.content;
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

    const { total, orderStatus, products, ticket } = this.orderForm.value;

    const orderPayload = {
      total: total || 0,
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
      productId: product.id,
      quantity: 1
    }));
  }

  private loadOrder(): void {
    if (!this.orderId) return;

    this.orderService.findById(this.orderId).pipe(
      switchMap(order => {

        const ticket$ = order.ticketId ? this.ticketService.findById(order.ticketId) : of(null);

        const productIds = order.products.map((p: any) => p.productId);
        const products$ = this.productService.findAllDTO(productIds);

        return forkJoin({
          order: of(order), // Mantemos a 'order' original
          ticket: ticket$,
          products: products$
        });
      })
    ).subscribe(({ order, ticket, products }) => {

      this.orderForm.patchValue({
        total: order.total,
        ticket: ticket,
        products: products.content
      });
    });
  }

  private createOrder(payload: any): void {
    console.log("Enviando para o backend:", payload);
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
