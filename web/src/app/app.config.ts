import { ApplicationConfig } from '@angular/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideRouter } from '@angular/router';

import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import Aura from '@primeuix/themes/aura';
import { providePrimeNG } from 'primeng/config';
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimationsAsync(),
    provideClientHydration(withEventReplay()),
    provideHttpClient(withInterceptorsFromDi()),
    providePrimeNG({
      theme: {
        preset: Aura
      }
    })
  ]
};
