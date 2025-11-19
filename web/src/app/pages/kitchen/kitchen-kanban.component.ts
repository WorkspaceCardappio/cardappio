import {CommonModule, DatePipe} from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, OnDestroy } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { DragDropModule, CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { interval, Subscription } from 'rxjs';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { TagModule } from 'primeng/tag';
import { SkeletonModule } from 'primeng/skeleton';
import {OrderService} from "../order/service/order.service";

// CORREÇÃO 1: Usar os nomes literais das constantes do Enum como strings para o filtro RSQL.
const STATUS_NAMES = {
  PENDING: "PENDING",
  IN_PROGRESS: "IN_PROGRESS",
  // O nome do Enum para o código 4 é DELIVERED
  DELIVERED: "DELIVERED"
}

// Mantemos esta interface para a tipagem dos dados recebidos, mas o filtro RSQL usará STATUS_NAMES.
interface KitchenOrder {
  id: string;
  number: number;
  ticket: { number: string };
  status: { code: number; description: string };
  createdAt: string;
}

// Revertemos para os códigos numéricos para o SWITCH/CASE no HTML e para a API de alteração de status.
const STATUS_CODES = {
  PENDING: 1,
  IN_PROGRESS: 2,
  DELIVERED: 4
}


@Component({
  selector: 'app-kitchen-kanban',
  standalone: true,
  imports: [
    CommonModule,
    ButtonModule,
    CardModule,
    DragDropModule,
    BreadcrumbModule,
    TagModule,
    DatePipe,
    SkeletonModule
  ],
  providers: [OrderService],
  templateUrl: './kitchen-kanban.component.html',
  styleUrl: './kitchen-kanban.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class KitchenKanbanComponent implements OnInit, OnDestroy {

  home = { icon: 'pi pi-home', routerLink: '/home' };
  items = [{ label: 'Cozinha Kanban', routerLink: '/kitchen' }];

  pending: KitchenOrder[] = [];
  inProgress: KitchenOrder[] = [];
  ready: KitchenOrder[] = [];

  loading = false;
  private refreshSubscription: Subscription | null = null;

  constructor(
    private service: OrderService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadOrders();
    this.refreshSubscription = interval(30000).subscribe(() => this.loadOrders());
  }

  ngOnDestroy(): void {
    this.refreshSubscription?.unsubscribe();
  }

  loadOrders() {
    this.loading = true;

    // CORREÇÃO 2: Usar STATUS_NAMES (PENDING, IN_PROGRESS, DELIVERED) para o filtro RSQL.
    const relevantStatuses = [
      STATUS_NAMES.PENDING,
      STATUS_NAMES.IN_PROGRESS,
      STATUS_NAMES.DELIVERED
    ].join(',');

    // O filtro RSQL agora será: status=in=(PENDING,IN_PROGRESS,DELIVERED)
    const rsqlFilter = `status=in=(${relevantStatuses})`;
    const request = `?pageSize=100&search=${rsqlFilter}`;

    this.service.findAllDTO(request).subscribe({
      next: (response) => {
        this.pending = [];
        this.inProgress = [];
        this.ready = [];

        response.content.forEach((order: KitchenOrder) => {
          // Aqui no frontend, continuamos a usar o DTO que tem o 'code' para distribuição/exibição
          switch (order.status.code) {
            case STATUS_CODES.PENDING:
              this.pending.push(order);
              break;
            case STATUS_CODES.IN_PROGRESS:
              this.inProgress.push(order);
              break;
            case STATUS_CODES.DELIVERED:
              this.ready.push(order);
              break;
          }
        });

        this.loading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Erro ao carregar pedidos para o Kanban', err);
        this.loading = false;
        this.cdr.markForCheck();
      }
    });
  }

  drop(event: CdkDragDrop<KitchenOrder[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );

      const newColumnId = event.container.id;
      const order = event.container.data[event.currentIndex];

      let newStatusCode: number;

      switch (newColumnId) {
        case 'pendingList':
          newStatusCode = STATUS_CODES.PENDING;
          break;
        case 'inProgressList':
          newStatusCode = STATUS_CODES.IN_PROGRESS;
          break;
        case 'readyList':
          newStatusCode = STATUS_CODES.DELIVERED;
          break;
        default:
          return;
      }

      this.updateOrderStatus(order.id, newStatusCode);
    }
  }

  updateOrderStatus(orderId: string, statusCode: number) {
    this.service.changeStatus(orderId, String(statusCode)).subscribe({
      next: () => {
        console.log(`Pedido ${orderId} atualizado para status ${statusCode}`);
        this.loadOrders();
      },
      error: (err) => {
        console.error(`Erro ao atualizar status do pedido ${orderId}`, err);
        this.loadOrders();
      }
    });
  }
}
