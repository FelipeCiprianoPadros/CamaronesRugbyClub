import { Component } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true,
  imports:[
    RouterModule,
    LoginComponent
  ]
})
export class HomeComponent {
  constructor(private router:Router) {}

  clearRouterOutlet() {
    this.router.navigate(['/']);
  }
}
