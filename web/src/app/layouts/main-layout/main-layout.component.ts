import { NgClass } from "@angular/common";
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from '../../menu-component/header/header.component';
import { LeftMenuComponent } from '../../menu-component/left-menu/left-menu.component';
import { routesMenu } from '../../routes-menu.config';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [LeftMenuComponent, RouterOutlet, HeaderComponent, NgClass],
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MainLayoutComponent {
  protected readonly routesMenu = routesMenu;
}
