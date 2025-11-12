import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

export const roleGuard: CanActivateFn = (route, state) => {
  if (typeof window === 'undefined') {
    return true;
  }

  const keycloak = inject(KeycloakService);
  const router = inject(Router);

  const requiredRoles: string[] = route.data['roles'] || [];

  if (requiredRoles.length === 0) {
    return true;
  }

  const hasRole = requiredRoles.some((role) =>
    keycloak.isUserInRole(role)
  );

  if (!hasRole) {
    router.navigate(['/unauthorized']);
    return false;
  }

  return true;
};