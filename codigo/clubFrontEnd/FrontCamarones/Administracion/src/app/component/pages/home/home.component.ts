import { Component, computed, signal } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { LoginComponent } from '../../auth/login/login.component';
import { FooterComponent } from '../../shared/footer/footer.component';
import { MatSidenav, MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import{MatToolbarModule} from '@angular/material/toolbar'
import { SidenavComponent } from '../../shared/sidenav/sidenav.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true,
  imports:[
    RouterModule,
    LoginComponent,
    FooterComponent,
    MatMenuModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    SidenavComponent
  ]
})
export class HomeComponent {
  constructor(private router:Router) {}

  collapsed =signal(false)
  
  sidenavWidth= computed(()=> this.collapsed()? '250px': '60px');

  clearRouterOutlet() {
    this.router.navigate(['/']);
  }

}
