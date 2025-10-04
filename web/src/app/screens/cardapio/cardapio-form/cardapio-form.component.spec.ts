import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardapioFormComponent } from './cardapio-form.component';
import { provideHttpClient } from "@angular/common/http";
import { provideHttpClientTesting } from "@angular/common/http/testing";
import { provideRouter } from "@angular/router";

describe('CardapioFormComponent', () => {
  let component: CardapioFormComponent;
  let fixture: ComponentFixture<CardapioFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardapioFormComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardapioFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
