import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ProductItemService } from '../service/product-item.service';

describe('ProductItemService', () => {
  let service: ProductItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(ProductItemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
