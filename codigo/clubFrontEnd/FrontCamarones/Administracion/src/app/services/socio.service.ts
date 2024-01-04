import { Injectable } from '@angular/core';
import { Socio } from '../models/Socio';
import { HttpClient, HttpHeaders, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OperadorService } from './operador.service';



@Injectable({
    providedIn: 'root'
  })

export class socioService{

  private url ='http://localhost:8080'
constructor(private http:HttpClient, private operadorService: OperadorService){}
    
add(socio: Socio): Observable<any>{
  return this.http.post(this.url, socio);
}

getAll(): Observable<any>{
  return this.http.get(this.url)
}

getById(SocioId: Socio): Observable<any>{
  return this.http.get(this.url + SocioId)
}
delete(SocioId: Socio): Observable<any>{
  return this.http.delete(this.url + SocioId)
}
}