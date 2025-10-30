import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'header-menu',
  imports: [CommonModule, ButtonModule],
  templateUrl: './header.component.html',
  standalone: true,
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {
  username: string = '';
  userRoles: string[] = [];
  isLoggedIn: boolean = false;
  userMenuItems: MenuItem[] = [];
  showUserMenu: boolean = false;

  constructor(public authService: AuthService) {}

  ngOnInit(): void {
    this.loadUserInfo();
    this.setupUserMenu();
  }

  private loadUserInfo(): void {
    this.isLoggedIn = this.authService.isLoggedIn();

    if (this.isLoggedIn) {
      this.username = this.authService.getUsername();
      this.userRoles = this.authService.getUserRoles();
    }
  }

  private setupUserMenu(): void {
    this.userMenuItems = [
      {
        label: 'Perfil',
        icon: 'pi pi-user',
      },
      {
        separator: true
      },
      {
        label: 'Sair',
        icon: 'pi pi-sign-out',
        command: () => this.logout()
      }
    ];
  }

  login(): void {
    this.authService.login();
  }

  logout(): void {
    this.authService.logout();
  }

  toggleUserMenu(): void {
    this.showUserMenu = !this.showUserMenu;
  }

  closeUserMenu(): void {
    this.showUserMenu = false;
  }


  getUserInitials(): string {
    if (!this.username) return 'U';
    return this.username.charAt(0).toUpperCase();
  }

  getRoleBadgeColor(): string {
    if (this.authService.isAdmin()) return 'danger';
    if (this.authService.isUser()) return 'success';
    return 'info';
  }

  getPrimaryRole(): string {
    if (this.authService.isAdmin()) return 'ADMIN';
    if (this.authService.isUser()) return 'USER';
    return 'GUEST';
  }
}
