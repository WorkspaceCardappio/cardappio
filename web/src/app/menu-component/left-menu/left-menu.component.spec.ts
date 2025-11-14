import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { KeycloakService } from 'keycloak-angular';
import { LeftMenuComponent } from './left-menu.component';

describe('LeftMenuComponent', () => {
  let component: LeftMenuComponent;
  let fixture: ComponentFixture<LeftMenuComponent>;

  const mockKeycloakService = {
    isLoggedIn: jasmine.createSpy('isLoggedIn').and.returnValue(false),
    getUsername: jasmine.createSpy('getUsername').and.returnValue(''),
    getUserRoles: jasmine.createSpy('getUserRoles').and.returnValue([]),
    isUserInRole: jasmine.createSpy('isUserInRole').and.returnValue(false),
    getKeycloakInstance: jasmine.createSpy('getKeycloakInstance').and.returnValue({}),
    loadUserProfile: jasmine.createSpy('loadUserProfile').and.returnValue(Promise.resolve(null)),
    getToken: jasmine.createSpy('getToken').and.returnValue(Promise.resolve('')),
    updateToken: jasmine.createSpy('updateToken').and.returnValue(Promise.resolve(false)),
    login: jasmine.createSpy('login').and.returnValue(Promise.resolve())
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeftMenuComponent],
      providers: [
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: KeycloakService, useValue: mockKeycloakService }
      ]
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
