import { ComponentFixture, TestBed } from '@angular/core/testing';

import { provideHttpClient } from "@angular/common/http";
import { provideHttpClientTesting } from "@angular/common/http/testing";
import { provideRouter } from "@angular/router";
import { OrderVariableComponent } from './order-variable.component';

describe('OrderVariableComponent', () => {
  let component: OrderVariableComponent;
  let fixture: ComponentFixture<OrderVariableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrderVariableComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(OrderVariableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
