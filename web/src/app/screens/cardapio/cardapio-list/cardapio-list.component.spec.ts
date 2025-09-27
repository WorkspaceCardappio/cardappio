import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardapioListComponent } from './cardapio-list.component';

describe('CardapioListComponent', () => {
  let component: CardapioListComponent;
  let fixture: ComponentFixture<CardapioListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardapioListComponent]
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
