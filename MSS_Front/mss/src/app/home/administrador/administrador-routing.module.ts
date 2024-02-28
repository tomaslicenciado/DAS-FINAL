import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdministradorMainComponent } from './components/administrador-main/administrador-main.component';
import { AbmBannersComponent } from './components/abm-banners/abm-banners.component';
import { AbmPlataformasComponent } from './components/abm-plataformas/abm-plataformas.component';
import { AbmPublicistasComponent } from './components/abm-publicistas/abm-publicistas.component';
import { AbmPublicidadesComponent } from './components/abm-publicidades/abm-publicidades.component';
import { BannersResolver, PlataformasResolver, PublicacionesResolver, PublicistasResolver } from 'src/app/api/resolvers/mss-api.service';

const routes: Routes = [
  {path: '', component: AdministradorMainComponent},
  {path: 'abm-banners', component: AbmBannersComponent, resolve: {banners: BannersResolver}},
  {path: 'abm-plataformas', component: AbmPlataformasComponent, resolve: {plataformas: PlataformasResolver}},
  {path: 'abm-publicistas', component: AbmPublicistasComponent, resolve: {publicistas: PublicistasResolver}},
  {path: 'abm-publicidades', component: AbmPublicidadesComponent, resolve: {publicidades: PublicacionesResolver}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministradorRoutingModule { }
