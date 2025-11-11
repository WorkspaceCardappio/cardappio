import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth/auth.service';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-unauthorized',
  standalone: true,
  imports: [CommonModule, ButtonModule, CardModule],
  template: `
    <div class="unauthorized-container">
      <p-card>
        <ng-template pTemplate="header">
          <div class="header-content">
            <i class="pi pi-ban" style="font-size: 4rem; color: #ef4444;"></i>
          </div>
        </ng-template>

        <div class="content">
          <h2>Acesso Negado</h2>
          <p>Você não tem permissão para acessar esta página.</p>
          <p class="user-info" *ngIf="username">
            Usuário logado: <strong>{{ username }}</strong>
          </p>
          <p class="roles-info" *ngIf="roles.length > 0">
            Suas permissões: <strong>{{ roles.join(', ') }}</strong>
          </p>
        </div>

        <ng-template pTemplate="footer">
          <div class="actions">
            <p-button
              label="Voltar para Home"
              icon="pi pi-home"
              (onClick)="goHome()"
              severity="secondary">
            </p-button>

            <p-button
              label="Fazer Logout"
              icon="pi pi-sign-out"
              (onClick)="logout()"
              severity="danger">
            </p-button>
          </div>
        </ng-template>
      </p-card>
    </div>
  `,
  styles: [`
    .unauthorized-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      padding: 2rem;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    }

    p-card {
      width: 100%;
      max-width: 500px;
    }

    .header-content {
      display: flex;
      justify-content: center;
      padding: 2rem 0;
    }

    .content {
      text-align: center;
      padding: 1rem;
    }

    .content h2 {
      color: #ef4444;
      margin-bottom: 1rem;
    }

    .content p {
      color: #6b7280;
      margin-bottom: 0.5rem;
    }

    .user-info, .roles-info {
      margin-top: 1rem;
      font-size: 0.9rem;
    }

    .actions {
      display: flex;
      gap: 1rem;
      justify-content: center;
      flex-wrap: wrap;
    }
  `]
})
export class UnauthorizedComponent {
  username: string = '';
  roles: string[] = [];

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    this.username = this.authService.getUsername();
    this.roles = this.authService.getUserRoles();
  }

  goHome(): void {
    this.router.navigate(['/']);
  }

  logout(): void {
    this.authService.logout();
  }
}