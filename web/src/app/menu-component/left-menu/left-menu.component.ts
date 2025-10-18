import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'left-menu',
  imports: [CommonModule],
  templateUrl: './left-menu.component.html',
  styleUrl: './left-menu.component.scss',
  standalone: true
})
export class LeftMenuComponent {

  @Input({ required: true }) routes: any[] = [];

  expanded = false;

  constructor(public readonly router: Router) {}

  toggleExpand(): void {
    this.expanded = !this.expanded;
  }

  redirectTo(path: string): void {
    this.router.navigate([path]);
  }
}
