import { HttpClient, HttpHandler } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { TableStatusService } from '../table-status.service';

describe('table-status.service.spec.ts', () => {
  let service: TableStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HttpClient, HttpHandler, TableStatusService]
    });

    service = TestBed.inject(TableStatusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
