import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { UserRegistration, UserProfile } from '../models/user.model';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserManagementService {
  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  registerUser(user: UserRegistration): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/register`, {
      username: user.username,
      email: user.email,
      firstName: user.firstName,
      lastName: user.lastName,
      cpf: user.cpf,
      password: user.password
    });
  }

  getCurrentUserProfile(): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.apiUrl}/auth/profile`);
  }

  updateUserProfile(profile: Partial<UserProfile>): Observable<UserProfile> {
    return this.http.put<UserProfile>(`${this.apiUrl}/auth/profile`, profile);
  }

  checkUsernameExists(username: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/auth/check-username/${username}`);
  }

  checkEmailExists(email: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/auth/check-email/${email}`);
  }

  checkCpfExists(cpf: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/auth/check-cpf/${cpf}`);
  }
}
