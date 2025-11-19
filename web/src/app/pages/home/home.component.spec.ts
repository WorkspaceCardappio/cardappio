import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './home.component';
import { Chart, registerables } from 'chart.js';
import { WebSocketService } from '../../core/services/websocket.service';
import { of } from 'rxjs';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  const mockWebSocketService = {
    getOrderEvents: () => of(null),
    getTicketEvents: () => of(null),
    getConnectionStatus: () => of(false),
    disconnect: () => {}
  };

  beforeAll(() => {
    Chart.register(...registerables);
  });

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        HomeComponent
      ],
      providers: [
        { provide: WebSocketService, useValue: mockWebSocketService }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});