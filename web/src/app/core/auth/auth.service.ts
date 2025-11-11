import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginCredentials, KeycloakTokenResponse } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = environment.apiUrl;

  constructor(
    private keycloakService: KeycloakService,
    private http: HttpClient
  ) {}

  private isBrowser() {
    return typeof window !== 'undefined';
  }

  async getUserProfile(): Promise<KeycloakProfile | null> {
    if (!this.isBrowser()) return null;

    try {
      return await this.keycloakService.loadUserProfile();
    } catch (error) {
      console.error('Erro ao carregar perfil do usuário', error);
      return null;
    }
  }

  getUsername() {
    if (!this.isBrowser()) return '';
    try {
      return this.keycloakService.getUsername();
    } catch (error) {
      return '';
    }
  }

  getUserRoles() {
    if (!this.isBrowser()) return [];
    try {
      return this.keycloakService.getUserRoles();
    } catch (error) {
      return [];
    }
  }

  hasRole(role: string) {
    if (!this.isBrowser()) return false;
    return this.keycloakService.isUserInRole(role);
  }

  isAdmin() {
    return this.hasRole('ADMIN');
  }

  isUser() {
    return this.hasRole('USER');
  }

  isLoggedIn() {
    if (!this.isBrowser()) return false;
    return this.keycloakService.isLoggedIn();
  }

  login(): Promise<void> {
    if (!this.isBrowser()) return Promise.resolve();
    return this.keycloakService.login({
      redirectUri: window.location.origin + '/home'
    });
  }

  logout() {
    if (!this.isBrowser()) return;

    sessionStorage.removeItem('kc_token');
    sessionStorage.removeItem('kc_refresh_token');

    const keycloakInstance = this.keycloakService.getKeycloakInstance();
    keycloakInstance.token = undefined;
    keycloakInstance.refreshToken = undefined;
    keycloakInstance.idToken = undefined;
    keycloakInstance.authenticated = false;

    window.location.href = '/login';
  }

  async getToken(): Promise<string> {
    if (!this.isBrowser()) return '';
    return await this.keycloakService.getToken();
  }

  async updateToken(): Promise<boolean> {
    if (!this.isBrowser()) return false;
    return await this.keycloakService.updateToken(30);
  }

  async loginWithCredentials(credentials: LoginCredentials): Promise<void> {
    if (!this.isBrowser()) return;

    try {
      const response = await firstValueFrom(
        this.http.post<KeycloakTokenResponse>(
          `${this.apiUrl}/auth/login`,
          credentials
        )
      );

      if (response && response.access_token) {
        sessionStorage.setItem('kc_token', response.access_token);
        sessionStorage.setItem('kc_refresh_token', response.refresh_token);

        const keycloakInstance = this.keycloakService.getKeycloakInstance();

        const base64Url = response.access_token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(
          atob(base64)
            .split('')
            .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
            .join('')
        );
        const tokenParsed = JSON.parse(jsonPayload);

        keycloakInstance.token = response.access_token;
        keycloakInstance.refreshToken = response.refresh_token;
        keycloakInstance.idToken = response.access_token;
        keycloakInstance.tokenParsed = tokenParsed;
        keycloakInstance.authenticated = true;

        window.location.href = '/home';
      }
    } catch (error) {
      console.error('Erro no login customizado:', error);
      throw error;
    }
  }
}
