import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SuscriptorMainComponent } from './components/suscriptor-main/suscriptor-main.component';
import { CatalogoResolver, GenerosResolver, BannersResolver, PlataformasResolver, PublicacionesResolver } from 'src/app/api/resolvers/mss-api.service';
import { ReactiveFormsModule } from '@angular/forms';
import { FinalizarFederacionComponent } from './components/finalizar-federacion/finalizar-federacion.component';
import { VisualizarContenidoComponent } from './components/visualizar-contenido/visualizar-contenido.component';

const routes: Routes = [
  {path: '', component: SuscriptorMainComponent, resolve: {publicaciones: PublicacionesResolver, catalogo: CatalogoResolver, plataformas: PlataformasResolver,
                                                            generos: GenerosResolver}},
  {path: 'finalizarFederacion/:id', component: FinalizarFederacionComponent},
  {path: 'visualizar-contenido', component: VisualizarContenidoComponent}
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    ReactiveFormsModule
  ],
  exports: [RouterModule]
})
export class SuscriptorRoutingModule { }
