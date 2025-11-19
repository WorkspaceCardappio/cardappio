import {
  CommonModule,
  DatePipe,

} from '@angular/common';
import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, OnDestroy, NgZone} from '@angular/core';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { DragDropModule, CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { interval, Subscription, Observable, of } from 'rxjs';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { TagModule } from 'primeng/tag';
import { SkeletonModule } from 'primeng/skeleton';
import { OrderService } from "../order/service/order.service";
import { finalize, timeout, catchError, tap } from 'rxjs/operators';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

const STATUS_NAMES = {
  PENDING: "PENDING",
  IN_PROGRESS: "IN_PROGRESS",
  DELIVERED: "DELIVERED",
  FINISHED: "FINISHED"
}

interface KitchenOrder {
// ... (definição da interface KitchenOrder) ...
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
    DatePipe,
    SkeletonModule,
    ToastModule
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
  private refreshSubscription: Subscription | null = null;
  private isUpdating = false;
  private loadingTimeout: any;

  constructor(
    private service: OrderService,
    private cdr: ChangeDetectorRef,
    private messageService: MessageService,
    private ngZone: NgZone // Injeção necessária para isolar o intervalo
  ) {}

  ngOnInit(): void {
    this.loadingTimeout = setTimeout(() => {
      if (this.loading) {
        console.warn('Loading timeout - forçando exibição');
        this.loading = false;
        this.cdr.markForCheck();
      }
    }, 5000);

    this.loadOrders();

    // CORREÇÃO NG0506: Executa o interval FORA da zona do Angular.
    this.ngZone.runOutsideAngular(() => {
      this.refreshSubscription = interval(30000).subscribe(() => {
        if (!this.isUpdating) {
          // Volta para a zona APENAS para executar a lógica que altera o estado da UI/dados
          this.ngZone.run(() => {
            this.loadOrders();
          });
        }
      });
    });
  }

  ngOnDestroy(): void {
    this.refreshSubscription?.unsubscribe();
    if (this.loadingTimeout) {
      clearTimeout(this.loadingTimeout);
    }
  }

  loadOrders() {
    this.loading = true;
    this.cdr.markForCheck();

    const relevantStatuses = [
      STATUS_NAMES.PENDING,
      STATUS_NAMES.IN_PROGRESS,
      STATUS_NAMES.DELIVERED
    ].join(',');

    const rsqlFilter = `status=in=(${relevantStatuses})`;
    const request = `?pageSize=100&search=${rsqlFilter}`;

    this.service.findAllDTO(request)
      .pipe(
        timeout(10000),
        catchError((error) => {
          console.error('Erro ao carregar pedidos:', error);
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
              console.warn('Pedido sem status válido:', order);
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

          console.log('✅ Pedidos carregados:', {
            pending: this.pending.length,
            inProgress: this.inProgress.length,
            ready: this.ready.length,
            total: response.content.length
          });
          this.cdr.markForCheck(); // Força a atualização da UI após carregar os dados
        }
      });
  }

  /**
   * Manipula o evento de soltura (drop) do CdkDragDrop.
   * Aciona a chamada à API para atualização do status do pedido.
   * @param event Evento de drop.
   */
  drop(event: CdkDragDrop<KitchenOrder[]>) {

    console.log("droppou?")
    if (event.previousContainer === event.container) {
      console.log("bk1")
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
      this.cdr.detectChanges();
      return;
    }

    if (this.isUpdating) {
      console.log("bk2")
      console.warn('[DROP] 🛑 Atualização em andamento. Ignorando novo drop.');
      return;
    }

    console.log("bk3")
    this.isUpdating = true;

    const order = event.previousContainer.data[event.previousIndex];
    const previousContainerData = event.previousContainer.data;
    const previousIndex = event.previousIndex;
    const newColumnId = event.container.id;

    let newStatusCode: number;
    let newStatusName: string;
    let newStatusDescription: string;

    console.log("bk4")

    console.log(`Tentando mover Pedido #${order.number} para coluna ${newColumnId}`);

    switch (newColumnId) {
      case 'cdk-drop-list-0':
        newStatusCode = STATUS_CODES.PENDING;
        newStatusName = STATUS_NAMES.PENDING;
        newStatusDescription = 'Pendente';
        break;
      case 'cdk-drop-list-1':
        newStatusCode = STATUS_CODES.IN_PROGRESS;
        newStatusName = STATUS_NAMES.IN_PROGRESS;
        newStatusDescription = 'Em Andamento';
        break;
      case 'cdk-drop-list-2':
        newStatusCode = STATUS_CODES.DELIVERED;
        newStatusName = STATUS_NAMES.DELIVERED;
        newStatusDescription = 'Pronto';
        break;
      default:
        this.isUpdating = false;
        console.log('io?')
        return;
    }

    console.log("bk4.5")


    // Backup do status original
    const oldStatus = { ...order.status };

    // 1. Mover visualmente ANTES de chamar API (melhora UX)
    transferArrayItem(
      event.previousContainer.data,
      event.container.data,
      event.previousIndex,
      event.currentIndex,
    );

    console.log("bk5")


    // 2. Atualizar status local (para refletir na tag, etc.)
    order.status = {
      code: newStatusCode,
      description: newStatusDescription,
      name: newStatusName
    };

    this.cdr.detectChanges(); // Força a atualização visual imediata



    console.log(`[DROP] ✅ Movimento visual e estado local atualizados. Pedido #${order.number}`);
    console.log(`[API_CALL] ➡️ Chamando service.changeStatus para ID=${order.id}, STATUS=${newStatusCode}`); // CONSOLE CHAVE

    // 3. Chamar API para persistir a mudança
    this.service.changeStatus(order.id, String(newStatusCode))
      .pipe(
        // TAP: Se este console não aparecer, a chamada HTTP NÃO foi para a fila.
        tap(() => console.log(`[API_CALL] 🟢 Observable Ativo - Requisição HTTP deve estar no Network Tab!`)),
        timeout(5000),
        catchError((error) => {
          console.error('[API_CALL] ❌ Erro de HTTP ou Timeout:', error);

          // Mostrar mensagem de erro
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: `Falha ao atualizar pedido #${order.number} - Revertido.`
          });

          // 4. REVERTER mudança (rollback)
          order.status = oldStatus;
          transferArrayItem(
            event.container.data, // Container atual
            previousContainerData, // Container anterior (o de origem)
            event.currentIndex, // Posição atual
            previousIndex // Posição anterior
          );
          this.cdr.detectChanges(); // Força a reversão visual
          console.log('[DROP] ↩️ Rollback concluído devido a falha da API.');

          return of(null); // Retorna um Observable que completa para que a cadeia continue
        }),
        finalize(() => {
          this.isUpdating = false;
          this.cdr.markForCheck();
          console.log('[API_CALL] 🏁 Finalize do Observable (Fim do Pipeline).'); // CONSOLE CHAVE
        })
      )
      .subscribe({
        next: (response) => {
          if (response) {
            console.log(`[API_CALL] ✅ Resposta OK do servidor. Requisição concluída.`); // CONSOLE CHAVE

            this.messageService.add({
              severity: 'success',
              summary: 'Sucesso',
              detail: `Pedido #${order.number} movido para ${newStatusDescription}`,
              life: 2000
            });
          }
        },
        complete: () => {
          console.log('[API_CALL] 🛑 Subscrição COMPLETA.'); // CONSOLE CHAVE
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
