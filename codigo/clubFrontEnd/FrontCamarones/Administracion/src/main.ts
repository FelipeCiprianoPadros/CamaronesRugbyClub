import { bootstrapApplication } from '@angular/platform-browser';
import { HomeComponent } from './app/component/pages/home/home.component';
import { LoginComponent } from './app/component/auth/login/login.component';
import { provideRouter } from '@angular/router';
import{routes} from './app/app-routing.module';
import { provideAnimations } from '@angular/platform-browser/animations'
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { socioService } from './app/services/socio.service';

bootstrapApplication(HomeComponent,{
    providers:[HttpClientModule,
    provideRouter(routes),
    provideAnimations(),
    socioService
]
})
.catch(err=> console.error(err))