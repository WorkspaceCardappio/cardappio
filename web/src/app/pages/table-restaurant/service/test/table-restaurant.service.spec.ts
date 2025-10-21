import { HttpClient, HttpHandler } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { TableRestaurantService } from '../table-restaurant.service';

describe('table-restaurant.service.spec.ts', () => {
  let service: TableRestaurantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HttpClient, HttpHandler, TableRestaurantService]
    });

    service = TestBed.inject(TableRestaurantService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
