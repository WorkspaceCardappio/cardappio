import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent, LeftMenuComponent } from 'cardappio-component-hub';
import { routesMenu } from './routes-menu.config';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [LeftMenuComponent, HeaderComponent, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {

  protected readonly routesMenu = routesMenu;

  title: string = 'title';

}
