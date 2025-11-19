import { CommonModule, CurrencyPipe, isPlatformBrowser } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, OnDestroy, PLATFORM_ID, inject } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { DragDropModule, CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { interval, Subscription, of } from 'rxjs';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { TagModule } from 'primeng/tag';
import { SkeletonModule } from 'primeng/skeleton';
import { OrderService } from "../order/service/order.service";
import { finalize, timeout, catchError } from 'rxjs/operators';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { WebSocketService } from '../../core/services/websocket.service';

const STATUS_NAMES = {
  PENDING: "PENDING",
  IN_PROGRESS: "IN_PROGRESS",
  DELIVERED: "DELIVERED",
  FINISHED: "FINISHED"
}

interface KitchenOrder {
  id: string;
  number: number;
  ticket: {
    number: string;
    name?: string;
  };
  status: {
    code: number;
    description: string;
    name?: string;
  };
  createdAt: string;
  totalValue?: number;
  observation?: string;
  items?: Array<{
    id: string;
    product: {
      name: string;
      category?: string;
    };
    quantity: number;
    observation?: string;
    unitPrice?: number;
    totalPrice?: number;
  }>;
}

const STATUS_CODES = {
  PENDING: 1,
  IN_PROGRESS: 2,
  FINISHED: 3,
  DELIVERED: 4
}

// Mapa para conversão de código para nome do status
const STATUS_CODE_TO_NAME: { [key: number]: string } = {
  1: STATUS_NAMES.PENDING,
  2: STATUS_NAMES.IN_PROGRESS,
  3: STATUS_NAMES.FINISHED,
  4: STATUS_NAMES.DELIVERED
};

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
    SkeletonModule,
    ToastModule,
    CurrencyPipe // Adicionado CurrencyPipe para uso no TS (opcional, mas bom para tipagem)
  ],
  providers: [OrderService, MessageService],
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

  loading = true;
  wsConnected = false;
  private refreshSubscription: Subscription | null = null;
  private wsSubscription: Subscription | null = null;
  private wsStatusSubscription: Subscription | null = null;
  private isUpdating = false;
  private platformId = inject(PLATFORM_ID);
  private isBrowser = isPlatformBrowser(this.platformId);

  constructor(
    private service: OrderService,
    private cdr: ChangeDetectorRef,
    private messageService: MessageService,
    private webSocketService: WebSocketService
  ) {}

  ngOnInit(): void {
    // Só executa no browser, não no servidor (SSR)
    if (!this.isBrowser) {
      this.loading = false;
      return;
    }

    this.loadOrders();
    this.subscribeToWebSocket();

    this.refreshSubscription = interval(30000).subscribe(() => {
      if (!this.isUpdating) {
        this.loadOrders();
      }
    });
  }

  ngOnDestroy(): void {
    this.refreshSubscription?.unsubscribe();
    this.wsSubscription?.unsubscribe();
    this.wsStatusSubscription?.unsubscribe();
  }

  private subscribeToWebSocket(): void {
    this.wsSubscription = this.webSocketService.getOrderEvents()
      .pipe(
        catchError(() => {
          console.error('Erro ao processar evento de pedido');
          return of(null);
        })
      )
      .subscribe(event => {
        if (!event) return;
        console.log('Order event received in kitchen:', event);
        this.loadOrders();
      });

    this.wsStatusSubscription = this.webSocketService.getConnectionStatus()
      .pipe(
        catchError(() => {
          console.error('Erro ao monitorar status de conexão');
          return of(false);
        })
      )
      .subscribe(connected => {
        this.wsConnected = connected;
        this.cdr.markForCheck();
      });
  }

  loadOrders() {
    this.loading = true;
    this.cdr.markForCheck();

    const relevantStatuses = [
      STATUS_NAMES.PENDING,
      STATUS_NAMES.IN_PROGRESS,
      STATUS_NAMES.DELIVERED
    ].join(',');

    // Filtro para pedidos de hoje (00:00 até 23:59:59)
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);

    const formatLocalDateTime = (date: Date): string => {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
    };

    const startOfDay = formatLocalDateTime(today);
    const endOfDay = formatLocalDateTime(tomorrow);

    const rsqlFilter = `status=in=(${relevantStatuses});createdAt>=${startOfDay};createdAt<${endOfDay}`;
    const request = `pageSize=100&search=${rsqlFilter}`;

    this.service.findKitchenOrders(request)
      .pipe(
        timeout(10000),
        catchError((error) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: 'Falha ao carregar pedidos'
          });
          return of({ content: [] });
        }),
        finalize(() => {
          this.loading = false;
          this.cdr.markForCheck();
        })
      )
      .subscribe({
        next: (response: any) => {
          const newPending: KitchenOrder[] = [];
          const newInProgress: KitchenOrder[] = [];
          const newReady: KitchenOrder[] = [];

          response.content.forEach((order: KitchenOrder) => {
            if (!order.status || !order.status.code) {
              return;
            }

            switch (order.status.code) {
              case STATUS_CODES.PENDING:
                newPending.push(order);
                break;
              case STATUS_CODES.IN_PROGRESS:
                newInProgress.push(order);
                break;
              case STATUS_CODES.DELIVERED:
                newReady.push(order);
                break;
            }
          });

          this.pending = newPending;
          this.inProgress = newInProgress;
          this.ready = newReady;

          this.cdr.markForCheck();
        }
      });
  }

  /**
   * Manipula o evento de soltura (drop) do CdkDragDrop.
   */
  drop(event: CdkDragDrop<KitchenOrder[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
      this.cdr.detectChanges();
      return;
    }

    if (this.isUpdating) {
      return;
    }

    this.isUpdating = true;

    const order = event.previousContainer.data[event.previousIndex];
    const previousContainerData = event.previousContainer.data;
    const previousIndex = event.previousIndex;
    const newColumnId = event.container.id;

    let newStatusCode: number;
    let newStatusDescription: string;

    console.log('Novo ID da coluna:', newColumnId);

    // CORREÇÃO LÓGICA: Usa os IDs reais definidos no HTML
    switch (newColumnId) {
      case 'cdk-drop-list-0':
        newStatusCode = STATUS_CODES.PENDING;
        newStatusDescription = 'Pendente';
        break;
      case 'cdk-drop-list-1':
        newStatusCode = STATUS_CODES.IN_PROGRESS;
        newStatusDescription = 'Em Andamento';
        break;
      case 'cdk-drop-list-2':
        newStatusCode = STATUS_CODES.DELIVERED;
        newStatusDescription = 'Pronto';
        break;
      default:
        this.isUpdating = false;
        return;
    }

    const oldStatus = { ...order.status };
    const newStatusName = STATUS_CODE_TO_NAME[newStatusCode] || 'DESCONHECIDO';


    // 1. Mover visualmente ANTES de chamar API (melhora UX)
    transferArrayItem(
      event.previousContainer.data,
      event.container.data,
      event.previousIndex,
      event.currentIndex,
    );

    // 2. Atualizar status local (para refletir na tag, etc.)
    order.status = {
      code: newStatusCode,
      description: newStatusDescription,
      name: newStatusName
    };

    this.cdr.detectChanges(); // Força a atualização visual imediata

    // 3. Chamar API para persistir a mudança
    this.service.changeStatus(order.id, String(newStatusCode))
      .pipe(
        timeout(5000),
        catchError((error) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: `Falha ao atualizar pedido #${order.number} - Revertido.`
          });

          // 4. REVERTER mudança (rollback)
          order.status = oldStatus;
          transferArrayItem(
            event.container.data,
            previousContainerData,
            event.currentIndex,
            previousIndex
          );
          this.cdr.detectChanges();
          return of(null);
        }),
        finalize(() => {
          this.isUpdating = false;
          this.cdr.markForCheck();
        })
      )
      .subscribe({
        next: (response) => {
          if (response) {
            this.messageService.add({
              severity: 'success',
              summary: 'Sucesso',
              detail: `Pedido #${order.number} movido para ${newStatusDescription}`,
              life: 2000
            });
          }
        }
      });
  }

  // Método auxiliar para calcular tempo desde criação
  getTimeSince(createdAt: string): string {
    const now = new Date();
    const created = new Date(createdAt);
    const diffMs = now.getTime() - created.getTime();
    const diffMins = Math.floor(diffMs / 60000);

    if (diffMins < 1) return 'Agora';
    if (diffMins < 60) return `${diffMins}min`;
    const hours = Math.floor(diffMins / 60);
    const mins = diffMins % 60;
    return `${hours}h${mins > 0 ? mins + 'min' : ''}`;
  }

  // Método auxiliar para calcular total de itens
  getTotalItems(order: KitchenOrder): number {
    if (!order.items) return 0;
    return order.items.reduce((sum, item) => sum + item.quantity, 0);
  }
}
