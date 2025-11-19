import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { KeycloakService } from 'keycloak-angular';


export const defaultRedirectGuard: CanActivateFn = async () => {
  if (typeof window === 'undefined') {
    return true;
  }

  const authService = inject(AuthService);
  const keycloakService = inject(KeycloakService);
  const router = inject(Router);

  if (!authService.isLoggedIn()) {
    return false;
  }

  try {
    await keycloakService.updateToken(30);
  } catch (error) {
    console.error('Erro ao atualizar token no defaultRedirectGuard:', error);
    return false;
  }

  if (authService.isUser() && !authService.isAdmin()) {
    router.navigate(['/order']);
  } else {
    router.navigate(['/home']);
  }

  return false;
};
