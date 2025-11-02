import { Routes } from '@angular/router';
import { adminGuard } from './shared/guard/admin-guard';

export const routes: Routes = [
    {
        path: 'auth',
        loadChildren: () => import('./modules/auth/auth-module').then(m => m.AuthModule)
    },
    {
        path: 'application',
        loadChildren: () => import('./modules/application/application-module').then(m => m.ApplicationModule)
    },
    {
        path: 'admin',
        loadChildren: () => import('./modules/admin/admin-module').then(m => m.AdminModule),
        canActivate: [adminGuard]
    },
    {
        path: '**',
        redirectTo: 'application'
    }
];
