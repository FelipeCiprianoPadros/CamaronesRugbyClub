import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';

export const appConfig: ApplicationConfig = {
  providers: [AppRoutingModule]
};
