import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ProductIngredientComponent } from './product-ingredient.component';

describe('ProductIngredientComponent', () => {
  let component: ProductIngredientComponent;
  let fixture: ComponentFixture<ProductIngredientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductIngredientComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductIngredientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
