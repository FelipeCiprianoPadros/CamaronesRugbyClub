import { bootstrapApplication } from '@angular/platform-browser';
import { HomeComponent } from './app/component/home/home.component';
import { LoginComponent } from './app/component/login/login.component';
import { provideRouter } from '@angular/router';
import{routes} from './app/app-routing.module'

bootstrapApplication(HomeComponent,{
    providers:[

    provideRouter(routes)
    ]
})
.catch(err=> console.error(err))