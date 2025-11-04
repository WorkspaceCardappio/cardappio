import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private keycloakService: KeycloakService) {}

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

    try {
      const logoutUrl = this.keycloakService.getKeycloakInstance().createLogoutUrl({
        redirectUri: window.location.origin
      });
      window.location.href = logoutUrl;
    } catch (error) {
      this.keycloakService.logout(window.location.origin).catch(() => {
        window.location.href = window.location.origin;
      });
    }
  }

  async getToken(): Promise<string> {
    if (!this.isBrowser()) return '';
    return await this.keycloakService.getToken();
  }

  async updateToken(): Promise<boolean> {
    if (!this.isBrowser()) return false;
    return await this.keycloakService.updateToken(30);
  }
}
