import { Component } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true,
  imports:[
    RouterModule
  ]
})
export class HomeComponent {
  constructor(private router:Router) {}

  clearRouterOutlet() {
    this.router.navigate(['/']);
  }
}
