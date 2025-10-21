import { TestBed } from '@angular/core/testing';

import { provideHttpClient } from "@angular/common/http";
import { provideHttpClientTesting } from "@angular/common/http/testing";
import { AdditionalService } from './additional.service';

describe('AdditionalService', () => {
  let service: AdditionalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
      ]
    });
    service = TestBed.inject(AdditionalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
