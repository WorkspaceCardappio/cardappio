import { HttpClient, HttpHandler } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TableRestaurantFormComponent } from '../table-restaurant-form.component';

describe('table-restaurant-form.component.spec.ts', () => {

  let component: TableRestaurantFormComponent;
  let fixture: ComponentFixture<TableRestaurantFormComponent>;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [TableRestaurantFormComponent],
      providers: [HttpClient, HttpHandler],
    })
      .compileComponents();

    fixture = TestBed.createComponent(TableRestaurantFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {

    expect(component).toBeTruthy();
  });
});
