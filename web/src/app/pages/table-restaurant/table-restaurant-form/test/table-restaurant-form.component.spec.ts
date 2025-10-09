import { HttpClient, HttpHandler } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { provideRouter, ActivatedRoute } from '@angular/router';
import { TableRestaurantFormComponent } from '../table-restaurant-form.component';

describe('table-restaurant-form.component.spec.ts', () => {

  let component: TableRestaurantFormComponent;
  let fixture: ComponentFixture<TableRestaurantFormComponent>;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [
        TableRestaurantFormComponent,
        ReactiveFormsModule,
      ],
      providers: [
        HttpClient,
        HttpHandler,
        provideRouter([]),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { params: { id: 'new' } },
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(TableRestaurantFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
