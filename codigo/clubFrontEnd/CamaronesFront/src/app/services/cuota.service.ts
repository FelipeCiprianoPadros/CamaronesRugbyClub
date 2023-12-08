import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Cuota } from "../models/Cuota";



@Injectable({
    providedIn: 'root'
})
export class CuotaService{
    private url = 'http://localhost:8080/cuota'

    constructor(private http: HttpClient){}

    getAll(): Observable<any>{
        return this.http.get(this.url)
    }

    add(c:Cuota): Observable<any>{
        return this.http.post(this.url, c)
    }
}