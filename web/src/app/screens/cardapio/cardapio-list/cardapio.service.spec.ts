import { provideHttpClient } from "@angular/common/http";
import { provideHttpClientTesting } from "@angular/common/http/testing";

import { TestBed } from '@angular/core/testing';

import { CardapioService } from './cardapio.service';

describe('CardapioService', () => {
  let service: CardapioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
      ]
    });
    service = TestBed.inject(CardapioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
