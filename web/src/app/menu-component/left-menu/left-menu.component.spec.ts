import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeftMenuComponent } from './left-menu.component';

describe('LeftMenuComponent', () => {
  let component: LeftMenuComponent;
  let fixture: ComponentFixture<LeftMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeftMenuComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeftMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('toggle expanded', () => {

    expect(component.expanded).toBeFalse();

    component.toggleExpand();

    expect(component.expanded).toBeTrue();

    component.toggleExpand();

    expect(component.expanded).toBeFalse();
  });
});
