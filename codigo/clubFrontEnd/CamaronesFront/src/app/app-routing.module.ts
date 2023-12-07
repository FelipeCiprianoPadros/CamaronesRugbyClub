import { Routes , RouterModule } from "@angular/router";
import { GastoComponent } from "./component/gasto/gasto.component";
import { CuotaComponent } from "./component/cuota/cuota.component";
import { OperadorComponent } from "./component/operador/operador.component";
import { ProveedorComponent } from "./component/proveedor/proveedor.component";
import { SocioComponent } from "./component/socio/socio.component";
import { AppComponent } from "./app.component";
import { NgModule } from "@angular/core";




const routes: Routes= [
    {path: 'gasto', component:GastoComponent },
    {path: 'cuota', component:CuotaComponent },
    {path: 'operador', component:OperadorComponent },
    {path: 'proveedor', component:ProveedorComponent },
    {path: 'socio', component:SocioComponent },
    {path: 'inicio', component:AppComponent }
];

@NgModule({
    imports:[RouterModule.forRoot(routes)],
    exports:[RouterModule]
})
export class AppRoutingModule{}