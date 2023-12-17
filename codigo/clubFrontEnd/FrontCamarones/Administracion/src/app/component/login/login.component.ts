import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-login',
  standalone:true,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(private router: Router, private cdRef: ChangeDetectorRef){}

  login(){
    const loginExitoso =true;
    // aca se aplica la logica para el incio de sesion y si es exitosa


    if(loginExitoso){
      this.router.navigate(['home'])
      this.cdRef.detectChanges();
  }
  }

}
