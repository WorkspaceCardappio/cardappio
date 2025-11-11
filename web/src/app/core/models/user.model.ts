export interface UserRegistration {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  cpf: string;
  password: string;
  confirmPassword: string;
}

export interface UserProfile {
  id: string;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  cpf?: string;
  emailVerified: boolean;
  enabled: boolean;
  createdTimestamp?: number;
}

export interface LoginCredentials {
  username: string;
  password: string;
}

export interface KeycloakTokenResponse {
  access_token: string;
  expires_in: number;
  refresh_expires_in: number;
  refresh_token: string;
  token_type: string;
  'not-before-policy': number;
  session_state: string;
  scope: string;
}
