import { TestBed } from '@angular/core/testing';

import { provideHttpClient } from '@angular/common/http';
import { StockService } from '../service/stock.service';

describe('StockService', () => {
  let service: StockService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient()
      ]
    });
    service = TestBed.inject(StockService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
