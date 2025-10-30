import { KeycloakConfig } from 'keycloak-js';

export const keycloakConfig: KeycloakConfig = {
  url: 'http://localhost:8090',
  realm: 'cardappio-app',
  clientId: 'frontend-app',
};
