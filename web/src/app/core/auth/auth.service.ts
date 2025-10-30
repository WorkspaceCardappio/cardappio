import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private keycloakService: KeycloakService) {}

  private isBrowser(): boolean {
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

  getUsername(): string {
    if (!this.isBrowser()) return '';
    return this.keycloakService.getUsername();
  }

  getUserRoles(): string[] {
    if (!this.isBrowser()) return [];
    return this.keycloakService.getUserRoles();
  }

  hasRole(role: string): boolean {
    if (!this.isBrowser()) return false;
    return this.keycloakService.isUserInRole(role);
  }

  isAdmin(): boolean {
    return this.hasRole('ADMIN');
  }

  isUser(): boolean {
    return this.hasRole('USER');
  }

  isLoggedIn(): boolean {
    if (!this.isBrowser()) return false;
    return this.keycloakService.isLoggedIn();
  }

  login(): void {
    if (!this.isBrowser()) return;
    this.keycloakService.login();
  }

  logout(): void {
    if (!this.isBrowser()) return;
    this.keycloakService.logout(window.location.origin);
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