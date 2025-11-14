import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductProductItemComponent } from './product-product-item.component';

describe('ProductProductItemComponent', () => {
  let component: ProductProductItemComponent;
  let fixture: ComponentFixture<ProductProductItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductProductItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductProductItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
