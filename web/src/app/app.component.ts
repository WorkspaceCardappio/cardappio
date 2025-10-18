import { NgClass } from "@angular/common";
import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './menu-component/header/header.component';
import { LeftMenuComponent } from './menu-component/left-menu/left-menu.component';
import { routesMenu } from './routes-menu.config';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [LeftMenuComponent, RouterOutlet, HeaderComponent, NgClass],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {

  protected readonly routesMenu = routesMenu;

  title: string = 'title';

}
