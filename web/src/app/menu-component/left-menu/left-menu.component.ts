import { CommonModule, isPlatformBrowser } from '@angular/common';
import { ChangeDetectorRef, Component, Inject, Input, OnDestroy, OnInit, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { TooltipModule } from 'primeng/tooltip';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'left-menu',
  imports: [
    CommonModule,
    TooltipModule
  ],
  templateUrl: './left-menu.component.html',
  styleUrl: './left-menu.component.scss',
  standalone: true
})
export class LeftMenuComponent implements OnInit, OnDestroy {

  @Input({ required: true }) routes: any[] = [];

  expanded = false;
  isLoggedIn: boolean = false;
  username: string = '';
  showUserMenu: boolean = false;
  private checkInterval: any;

  constructor(
    public readonly router: Router,
    public authService: AuthService,
    private cdr: ChangeDetectorRef,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.updateUserState();

      this.checkInterval = setInterval(() => {
        this.updateUserState();
      }, 500);
    }
  }

  ngOnDestroy() {
    if (this.checkInterval) {
      clearInterval(this.checkInterval);
    }
  }

  private updateUserState() {
    const newLoggedInState = this.authService.isLoggedIn();

    if (this.isLoggedIn !== newLoggedInState) {
      this.isLoggedIn = newLoggedInState;

      if (this.isLoggedIn) {
        this.username = this.authService.getUsername();
      } else {
        this.username = '';
        this.showUserMenu = false;
      }

      this.cdr.markForCheck();
    }
  }

  toggleExpand(): void {
    this.expanded = !this.expanded;
  }

  redirectTo(path: string): void {
    this.router.navigate([path]);
  }

  toggleUserMenu() {
    this.expanded = true;
    this.showUserMenu = !this.showUserMenu;
  }

  closeUserMenu() {
    this.showUserMenu = false;
  }

  logout() {
    try {
      this.showUserMenu = false;
      setTimeout(() => {
        this.authService.logout();
      }, 0);
    } catch (error) {
      console.error('Erro ao fazer logout:', error);
    }
  }

  getUserInitials() {
    if (!this.username) return 'U';
    return this.username.charAt(0).toUpperCase();
  }

  getPrimaryRole() {
    if (this.authService.isAdmin()) return 'ADMIN';
    if (this.authService.isUser()) return 'USER';
    return 'GUEST';
  }
}
