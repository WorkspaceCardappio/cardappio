import { ComponentFixture, TestBed } from '@angular/core/testing';

import { provideHttpClient } from '@angular/common/http';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { StockFormComponent } from './stock-form.component';

describe('StockFormComponent', () => {
  let component: StockFormComponent;
  let fixture: ComponentFixture<StockFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideRouter([]),
        provideNoopAnimations(),
      ],
      imports: [StockFormComponent]
    })

    .compileComponents();

    fixture = TestBed.createComponent(StockFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
