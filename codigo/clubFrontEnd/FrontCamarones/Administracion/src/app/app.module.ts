import { NgModule } from "@angular/core";
import { AppComponent } from "./app.component";
import { CuotaComponent } from "./component/cuota/cuota.component";
import { GastoComponent } from "./component/gasto/gasto.component";
import { OperadorComponent } from "./component/operador/operador.component";
import { ProveedorComponent } from "./component/proveedor/proveedor.component";
import { SocioComponent } from "./component/socio/socio.component";
import { BrowserModule } from "@angular/platform-browser";
import { AppRoutingModule } from "./app-routing.module";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

@NgModule({
    declarations:[
        CuotaComponent,
        GastoComponent,
        OperadorComponent,
        ProveedorComponent,
        SocioComponent,
        
    ],

    imports:[
        BrowserModule,
        AppRoutingModule,
        HttpClientModule, 
        ReactiveFormsModule,
        FormsModule
    ],
    providers: [],
    bootstrap:[]
    

})
export class AppModule{}