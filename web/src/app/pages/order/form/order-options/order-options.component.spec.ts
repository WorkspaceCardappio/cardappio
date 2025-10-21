import { ComponentFixture, TestBed } from '@angular/core/testing';

import { provideHttpClient } from "@angular/common/http";
import { provideHttpClientTesting } from "@angular/common/http/testing";
import { provideRouter } from "@angular/router";
import { OrderOptionsComponent } from './order-options.component';

describe('OrderOptionsComponent', () => {
  let component: OrderOptionsComponent;
  let fixture: ComponentFixture<OrderOptionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrderOptionsComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(OrderOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
