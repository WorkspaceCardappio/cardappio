import { TestBed } from '@angular/core/testing';
import { TicketService } from './ticket.service';
import { provideHttpClient } from "@angular/common/http";

describe('TicketService', () => {
  let service: TicketService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        TicketService,
        provideHttpClient(),
      ],
    });

    service = TestBed.inject(TicketService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
