import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(private router: Router){}

  login(){
    // aca se aplica la logica para el incio de sesion y si es exitosa
    this.router.navigate(['/home'])
  }
}
