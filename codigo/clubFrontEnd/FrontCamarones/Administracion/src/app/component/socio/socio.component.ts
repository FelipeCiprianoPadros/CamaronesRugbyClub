import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Socio } from '../../models/Socio';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-socio',
  templateUrl: './socio.component.html',
  styleUrl: './socio.component.css',
  standalone: true,
  imports:[FormsModule,ReactiveFormsModule,CommonModule]
})
export class SocioComponent implements OnInit {

  public form:FormGroup;

  socio: Socio = new Socio();

  ngOnInit(): void {
    this.socio.nombre="";
    this.socio.apellido="";
    this.socio.contacto="";
    this.socio.fechaNacimiento=null;
    this.form = new FormGroup({
      'Nombre':new FormControl(this.socio.nombre,[Validators.required]),
      "Apellido":new FormControl(this.socio.apellido,[Validators.required]),
      'Contacto':new FormControl(this.socio.contacto,[Validators.required]),
      'FchNacimiento':new FormControl(this.socio.fechaNacimiento,[Validators.required])
    });
  }
  get Apellido(){return this.form.get('Apellido')}
  get Nombre(){return this.form.get('Nombre')}
  get Contacto(){return this.form.get('Contacto')}
  get FchNacimiento(){return this.form.get('FchNacimiento')}

  addSocio(){
    let socio = new Socio();
    socio.nombre = this.Nombre.value;
    socio.apellido = this.Apellido.value;
    socio.contacto = this.Contacto.value;
    socio.fechaNacimiento = this.FchNacimiento.value;
  }
  

}
