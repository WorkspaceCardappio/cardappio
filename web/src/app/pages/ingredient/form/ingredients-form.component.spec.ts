import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HttpClient, HttpHandler } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { IngredientsFormComponent } from './ingredients-form.component';

describe('IngredientsFormComponent', () => {
  let component: IngredientsFormComponent;
  let fixture: ComponentFixture<IngredientsFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IngredientsFormComponent],
      providers: [HttpClient, HttpHandler, {
        provide: ActivatedRoute,
        useValue: {snapshot: {params: {id: 'new'}}}
      }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IngredientsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
