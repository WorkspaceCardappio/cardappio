import { ComponentFixture, TestBed } from '@angular/core/testing';

import { provideHttpClient } from "@angular/common/http";
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { TicketListComponent } from './ticket-list.component';
import { WebSocketService } from '../../../core/services/websocket.service';
import { of } from 'rxjs';

describe('TicketListComponent', () => {
  let component: TicketListComponent;
  let fixture: ComponentFixture<TicketListComponent>;

  const mockWebSocketService = {
    getOrderEvents: () => of(null),
    getTicketEvents: () => of(null),
    getConnectionStatus: () => of(false),
    disconnect: () => {}
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketListComponent],
      providers: [
        provideHttpClient(),
        provideRouter([]),
        provideNoopAnimations(),
        { provide: WebSocketService, useValue: mockWebSocketService }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
