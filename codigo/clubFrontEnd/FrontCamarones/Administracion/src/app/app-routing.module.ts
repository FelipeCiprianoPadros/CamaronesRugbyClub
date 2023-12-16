import { Routes , RouterModule } from "@angular/router";
import { CuotaComponent } from "./component/cuota/cuota.component";
import { OperadorComponent } from "./component/operador/operador.component";
import { ProveedorComponent } from "./component/proveedor/proveedor.component";
import { SocioComponent } from "./component/socio/socio.component";

import { LoginComponent } from "./component/login/login.component";
import { HomeComponent } from "./component/home/home.component";


export const routes: Routes= [
    { path: 'gasto', loadComponent: () => import('./component/gasto/gasto.component').then(m => m.GastoComponent) },
    { path: 'cuota', loadComponent: () => import('./component/cuota/cuota.component').then(m => m.CuotaComponent) },
    { path: 'operador', loadComponent: () => import('./component/operador/operador.component').then(m => m.OperadorComponent) },
    { path: 'socio', loadComponent: () => import('./component/socio/socio.component').then(m => m.SocioComponent) },
    { path: 'proveedor', loadComponent: () => import('./component/proveedor/proveedor.component').then(m => m.ProveedorComponent) },
    { path: 'login', loadComponent: () => import('./component/login/login.component').then(m => m.LoginComponent) },
    { path: 'home', loadComponent: () => import('./component/home/home.component').then(m => m.HomeComponent) },
    
    
];
