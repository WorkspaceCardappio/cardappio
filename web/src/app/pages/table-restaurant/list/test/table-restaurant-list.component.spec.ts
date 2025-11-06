import { HttpClient, HttpHandler } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { TableRestaurantListComponent } from '../table-restaurant-list.component';

describe('table-restaurant-list.component.spec.ts', () => {

  let component: TableRestaurantListComponent;
  let fixture: ComponentFixture<TableRestaurantListComponent>;

  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [TableRestaurantListComponent],
      providers: [HttpClient, HttpHandler, provideRouter([])],
    })
      .compileComponents();

    fixture = TestBed.createComponent(TableRestaurantListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {

    expect(component).toBeTruthy();
  });
});
