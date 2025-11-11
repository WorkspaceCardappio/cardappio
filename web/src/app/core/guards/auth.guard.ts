import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

export const authGuard: CanActivateFn = async (route, state) => {
  if (typeof window === 'undefined') {
    return true;
  }

  const keycloak = inject(KeycloakService);
  const router = inject(Router);

  const isLoggedIn = keycloak.isLoggedIn();

  if (!isLoggedIn) {
    router.navigate(['/login'], {
      queryParams: { returnUrl: state.url }
    });
    return false;
  }

  return true;
};