import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ProductVariableComponent } from './product-variable.component';

describe('ProductVariableComponent', () => {
  let component: ProductVariableComponent;
  let fixture: ComponentFixture<ProductVariableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductVariableComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting()
      ]
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
