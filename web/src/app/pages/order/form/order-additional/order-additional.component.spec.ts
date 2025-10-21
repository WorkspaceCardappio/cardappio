import { ComponentFixture, TestBed } from '@angular/core/testing';

import { provideHttpClient } from "@angular/common/http";
import { provideHttpClientTesting } from "@angular/common/http/testing";
import { provideRouter } from "@angular/router";
import { OrderAdditionalComponent } from './order-additional.component';

describe('OrderAdditionalComponent', () => {
  let component: OrderAdditionalComponent;
  let fixture: ComponentFixture<OrderAdditionalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrderAdditionalComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(OrderAdditionalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
