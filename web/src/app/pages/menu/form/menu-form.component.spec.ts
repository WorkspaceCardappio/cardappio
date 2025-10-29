import { ComponentFixture, TestBed } from '@angular/core/testing';

import { provideHttpClient } from "@angular/common/http";
import { provideHttpClientTesting } from "@angular/common/http/testing";
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from "@angular/router";
import { MenuFormComponent } from './menu-form.component';

describe('MenuFormComponent', () => {
  let component: MenuFormComponent;
  let fixture: ComponentFixture<MenuFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MenuFormComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideNoopAnimations(),
        provideRouter([]),
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
