import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { from, switchMap, catchError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  if (typeof window === 'undefined') {
    return next(req);
  }

  const keycloak = inject(KeycloakService);

  const excludedUrls = [
    '/api/public/',
    '/assets/',
  ];

  const shouldExclude = excludedUrls.some((url) => req.url.includes(url));

  if (shouldExclude) {
    return next(req);
  }

  if (!keycloak.isLoggedIn()) {
    return next(req);
  }

  return from(keycloak.updateToken(30)).pipe(
    switchMap(() => keycloak.getToken()),
    switchMap((token) => {
      if (token) {
        const clonedReq = req.clone({
          setHeaders: {
            Authorization: `Bearer ${token}`,
          },
        });
        return next(clonedReq);
      }
      return next(req);
    }),
    catchError((error) => {
      console.error('Erro ao atualizar token:', error);
      return next(req);
    })
  );
};
