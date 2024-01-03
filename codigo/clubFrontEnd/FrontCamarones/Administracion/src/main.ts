import { bootstrapApplication } from '@angular/platform-browser';
import { HomeComponent } from './app/component/pages/home/home.component';
import { LoginComponent } from './app/component/auth/login/login.component';
import { provideRouter } from '@angular/router';
import{routes} from './app/app-routing.module';
import { provideAnimations } from '@angular/platform-browser/animations'

bootstrapApplication(HomeComponent,{
    providers:[
    provideRouter(routes),
    provideAnimations()
]
})
.catch(err=> console.error(err))