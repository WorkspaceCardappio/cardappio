import { KeycloakService } from 'keycloak-angular';
import { keycloakConfig } from '../keycloak.config';

export function initializeKeycloak(keycloak: KeycloakService) {
  return () => {
    if (typeof window === 'undefined') {
      console.log('⚠️ Keycloak: Pulando inicialização no SSR');
      return Promise.resolve();
    }

    return keycloak.init({
      config: keycloakConfig,
      initOptions: {
        onLoad: 'check-sso',
        checkLoginIframe: false,
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html',
      },
      enableBearerInterceptor: false,
      bearerPrefix: 'Bearer',
    }).then(() => {
      console.log('✅ Keycloak: Inicializado com sucesso');
    }).catch((error) => {
      console.error('❌ Keycloak: Erro ao inicializar', error);
    });
  };
}