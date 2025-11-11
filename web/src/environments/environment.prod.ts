export const environment = {
  production: true,
  apiUrl: 'https://cardappio-prod.up.railway.app/api',
  keycloak: {
    url: 'https://seu-keycloak.up.railway.app', // TODO: Alterar para a URL real do Keycloak no Railway
    realm: 'cardappio-app',
    clientId: 'frontend-app'
  }
};
