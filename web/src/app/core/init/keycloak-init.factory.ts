import { KeycloakService } from 'keycloak-angular';
import { keycloakConfig } from '../keycloak.config';

export function initializeKeycloak(keycloak: KeycloakService) {
  return () => {
    if (typeof window === 'undefined') {
      return Promise.resolve();
    }

    const savedToken = sessionStorage.getItem('kc_token');
    const savedRefreshToken = sessionStorage.getItem('kc_refresh_token');

    return keycloak.init({
      config: keycloakConfig,
      initOptions: {
        token: savedToken || undefined,
        refreshToken: savedRefreshToken || undefined,
        onLoad: 'check-sso',
        checkLoginIframe: false,
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html',
      },
      enableBearerInterceptor: false,
      bearerPrefix: 'Bearer',
    }).then(() => {
      console.log('Keycloak: Inicializado com sucesso');
    }).catch((error) => {
      console.error('❌ Keycloak: Erro ao inicializar', error);
    });
  };
}