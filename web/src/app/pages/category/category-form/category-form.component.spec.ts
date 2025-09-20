import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HttpClient, HttpHandler } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { CategoryFormComponent } from './category-form.component';

describe('TesteComponent', () => {
  let component: CategoryFormComponent;
  let fixture: ComponentFixture<CategoryFormComponent>;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [CategoryFormComponent],
      providers: [HttpClient, HttpHandler, {
        provide: ActivatedRoute,
        useValue: { snapshot: { params: { id: 'new' } }}
      }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
