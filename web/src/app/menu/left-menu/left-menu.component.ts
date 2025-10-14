import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'left-menu',
  imports: [CommonModule],
  templateUrl: './left-menu.component.html',
  standalone: true,
  styleUrl: './left-menu.component.scss'
})
export class LeftMenuComponent {

  @Input({ required: true }) routes: any;

  expanded = false;

  constructor(
    private readonly router: Router
  ) {}

  toggleExpand() {
    this.expanded = !this.expanded;
  }

  redirectTo(path: string) {
    this.router.navigate([path]);
  }
}
