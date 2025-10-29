import { ComponentFixture, TestBed } from '@angular/core/testing';

import { provideHttpClient } from "@angular/common/http";
import { provideRouter } from '@angular/router';
import { TicketListComponent } from './ticket-list.component';

describe('TicketListComponent', () => {
  let component: TicketListComponent;
  let fixture: ComponentFixture<TicketListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketListComponent],
      providers: [provideHttpClient(), provideRouter([])]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
