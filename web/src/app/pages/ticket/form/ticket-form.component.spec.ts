import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketFormComponent } from './ticket-form.component';
import { ActivatedRoute, provideRouter } from "@angular/router";
import { provideHttpClient } from "@angular/common/http";

describe('TicketFormComponent', () => {
  let component: TicketFormComponent;
  let fixture: ComponentFixture<TicketFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketFormComponent],
      providers: [
        provideHttpClient(),
        provideRouter([]),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              params: { id: 'new' }
            }
          }
        }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
