import { NgModule, inject } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/common/home/home.component';
import { MssApiService, UserResolver } from '../api/resolvers/mss-api.service';
import { authGuard } from '../auth/guards/auth.guard';
import { suscriptorGuard } from '../auth/guards/suscriptor.guard';
import { administradorGuard } from '../auth/guards/administrador.guard';
import { publicistaGuard } from '../auth/guards/publicista.guard';
import { DatosPersonalesComponent } from './components/common/datos-personales/datos-personales.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivate: [authGuard],
    resolve: { user: UserResolver },
    children: [
      {path: '', redirectTo: './suscriptor', pathMatch: 'full'},
      {path: 'suscriptor', loadChildren: () => import('./suscriptor/suscriptor.module').then(m => m.SuscriptorModule), canActivate: [suscriptorGuard]},
      {path: 'administrador', loadChildren: () => import('./administrador/administrador.module').then(m => m.AdministradorModule), canActivate: [administradorGuard]},
      {path: 'publicista', loadChildren: () => import('./publicista/publicista.module').then(m => m.PublicistaModule), canActivate: [publicistaGuard]},
      {path: 'cuenta', component: DatosPersonalesComponent, canActivate: [authGuard]}
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
