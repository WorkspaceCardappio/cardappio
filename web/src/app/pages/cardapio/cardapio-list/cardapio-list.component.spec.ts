import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardapioListComponent } from './cardapio-list.component';
import { provideHttpClient } from "@angular/common/http";
import { provideHttpClientTesting } from "@angular/common/http/testing";

describe('CardapioListComponent', () => {
  let component: CardapioListComponent;
  let fixture: ComponentFixture<CardapioListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardapioListComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardapioListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
