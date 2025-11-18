export interface Employee {
  id: string;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  enabled: boolean;
  emailVerified: boolean;
  roles: string[];
  createdTimestamp?: number;
}

export interface CreateEmployeeRequest {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  password: string;
  roles?: string[];
  enabled?: boolean;
  emailVerified?: boolean;
}

export interface UpdateEmployeeRequest {
  email?: string;
  firstName?: string;
  lastName?: string;
  roles?: string[];
  enabled?: boolean;
}
