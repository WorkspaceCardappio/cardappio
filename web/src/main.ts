import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { registerLocaleData } from '@angular/common';
import pt from '@angular/common/locales/pt';

registerLocaleData(pt, 'pt-BR'); // <-- O Registro Global

bootstrapApplication(AppComponent, appConfig)
  .catch(err => console.error(err));
