import { Routes , RouterModule } from "@angular/router";



export const routes: Routes= [
    { path: 'gasto', loadComponent: () => import('./component/gasto/gasto.component').then(m => m.GastoComponent) },
    { path: 'cuota', loadComponent: () => import('./component/cuota/cuota.component').then(m => m.CuotaComponent) },
    { path: 'operador', loadComponent: () => import('./component/operador/operador.component').then(m => m.OperadorComponent) },
    { path: 'socio', loadComponent: () => import('./component/socio/socio.component').then(m => m.SocioComponent) },
    { path: 'proveedor', loadComponent: () => import('./component/proveedor/proveedor.component').then(m => m.ProveedorComponent) },
    { path: 'login', loadComponent: () => import('./component/login/login.component').then(m => m.LoginComponent) },
    { path: 'home', loadComponent: () => import('./component/home/home.component').then(m => m.HomeComponent) },
    
    
];
