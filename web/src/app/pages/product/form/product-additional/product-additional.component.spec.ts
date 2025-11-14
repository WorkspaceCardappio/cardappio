import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ProductAdditionalComponent } from './product-additional.component';

describe('ProductAdditionalComponent', () => {
  let component: ProductAdditionalComponent;
  let fixture: ComponentFixture<ProductAdditionalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductAdditionalComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductAdditionalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
