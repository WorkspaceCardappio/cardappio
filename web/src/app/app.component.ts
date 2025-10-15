import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './menu/header/header.component';
import { routesMenu } from './routes-menu.config';
import { LeftMenuComponent } from "./menu/left-menu/left-menu.component";
import { NgClass } from "@angular/common";

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
