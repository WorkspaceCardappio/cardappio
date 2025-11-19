import { Injectable } from '@angular/core';
import { Client, IMessage, StompConfig, StompSubscription } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { environment } from '../../../environments/environment';

export enum EventType {
  CREATED = 'CREATED',
  UPDATED = 'UPDATED',
  DELETED = 'DELETED',
  STATUS_CHANGED = 'STATUS_CHANGED'
}

export interface OrderEvent {
  orderId: string;
  eventType: EventType;
  timestamp: string;
  data: any;
}

export interface TicketEvent {
  ticketId: string;
  eventType: EventType;
  timestamp: string;
  data: any;
}

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private client: Client | null = null;
  private orderSubject = new Subject<OrderEvent>();
  private ticketSubject = new Subject<TicketEvent>();
  private connectionStatus = new BehaviorSubject<boolean>(false);

  private orderSubscription: StompSubscription | null = null;
  private ticketSubscription: StompSubscription | null = null;

  constructor() {
    setTimeout(() => this.connect(), 0);
  }

  private connect(): void {
    try {
      const wsUrl = environment.apiUrl.replace('/api', '/ws');

      const stompConfig: StompConfig = {
        webSocketFactory: () => new SockJS(wsUrl),
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        debug: (str) => {
          if (!environment.production) {
            console.log('STOMP: ' + str);
          }
        },
        onConnect: () => {
          console.log('✅ WebSocket conectado');
          this.connectionStatus.next(true);
          this.subscribeToTopics();
        },
        onDisconnect: () => {
          console.warn('⚠️ WebSocket desconectado - tentando reconectar...');
          this.connectionStatus.next(false);
        },
        onStompError: (frame) => {
          console.error('Erro STOMP:', frame.headers['message'] || 'Erro desconhecido');
          this.connectionStatus.next(false);
        },
        onWebSocketError: (event) => {
          console.error('Erro WebSocket:', event);
          this.connectionStatus.next(false);
        }
      };

      this.client = new Client(stompConfig);
      this.client.activate();
    } catch (error) {
      console.error('Erro ao inicializar WebSocket:', error);
      this.connectionStatus.next(false);
    }
  }

  private subscribeToTopics(): void {
    if (!this.client) return;

    try {
      this.orderSubscription = this.client.subscribe('/topic/orders', (message: IMessage) => {
        try {
          const event: OrderEvent = JSON.parse(message.body);
          this.orderSubject.next(event);
        } catch (error) {
          console.error('Erro ao processar mensagem de pedido:', error);
        }
      });

      this.ticketSubscription = this.client.subscribe('/topic/tickets', (message: IMessage) => {
        try {
          const event: TicketEvent = JSON.parse(message.body);
          this.ticketSubject.next(event);
        } catch (error) {
          console.error('Erro ao processar mensagem de comanda:', error);
        }
      });
    } catch (error) {
      console.error('Erro ao se inscrever nos tópicos WebSocket:', error);
      this.connectionStatus.next(false);
    }
  }

  public getOrderEvents(): Observable<OrderEvent> {
    return this.orderSubject.asObservable();
  }

  public getTicketEvents(): Observable<TicketEvent> {
    return this.ticketSubject.asObservable();
  }

  public getConnectionStatus(): Observable<boolean> {
    return this.connectionStatus.asObservable();
  }

  public disconnect(): void {
    if (this.orderSubscription) {
      this.orderSubscription.unsubscribe();
    }
    if (this.ticketSubscription) {
      this.ticketSubscription.unsubscribe();
    }
    if (this.client) {
      this.client.deactivate();
    }
  }
}
