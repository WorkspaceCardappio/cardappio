import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { routesMenu } from './routes-menu.config';
import { NgClass } from "@angular/common";
import { LeftMenuComponent } from "./menu-component/left-menu/left-menu.component";
import { HeaderComponent } from "./menu-component/header/header.component";

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
