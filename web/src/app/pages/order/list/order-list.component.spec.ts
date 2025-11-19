import { ComponentFixture, TestBed } from '@angular/core/testing';

import { provideHttpClient } from "@angular/common/http";
import { provideHttpClientTesting } from "@angular/common/http/testing";
import { provideRouter } from '@angular/router';
import { OrderListComponent } from "./order-list.component";
import { WebSocketService } from '../../../core/services/websocket.service';
import { of } from 'rxjs';

describe('OrderListComponent', () => {
  let component: OrderListComponent;
  let fixture: ComponentFixture<OrderListComponent>;

  const mockWebSocketService = {
    getOrderEvents: () => of(null),
    getTicketEvents: () => of(null),
    getConnectionStatus: () => of(false),
    disconnect: () => {}
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrderListComponent],
      providers: [
        provideHttpClient(),
        provideRouter([]),
        provideHttpClientTesting(),
        { provide: WebSocketService, useValue: mockWebSocketService }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(OrderListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
