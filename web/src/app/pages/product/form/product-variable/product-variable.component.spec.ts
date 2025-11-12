import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductVariableComponent } from './product-variable.component';

describe('ProductVariableComponent', () => {
  let component: ProductVariableComponent;
  let fixture: ComponentFixture<ProductVariableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductVariableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductVariableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
