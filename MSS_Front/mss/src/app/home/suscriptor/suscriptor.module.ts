import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SuscriptorRoutingModule } from './suscriptor-routing.module';
import { BarraFiltroComponent } from './components/barra-filtro/barra-filtro.component';
import { ListaContenidosComponent } from './components/lista-contenidos/lista-contenidos.component';
import { VisualizarContenidoComponent } from './components/visualizar-contenido/visualizar-contenido.component';
import { AdBannerComponent } from './components/ad-banner/ad-banner.component';
import { ItemContenidoComponent } from './components/item-contenido/item-contenido.component';
import { FiltroBusquedaPipe } from './pipes/filtro-busqueda.pipe';
import { ReactiveFormsModule } from '@angular/forms';
import { SuscriptorMainComponent } from './components/suscriptor-main/suscriptor-main.component';
import { FinalizarFederacionComponent } from './components/finalizar-federacion/finalizar-federacion.component';
import { VisualizacionService } from './services/visualizacion.service';
import { AdministrarPlataformasComponent } from './components/administrar-plataformas/administrar-plataformas.component';


@NgModule({
  declarations: [
    BarraFiltroComponent,
    ListaContenidosComponent,
    VisualizarContenidoComponent,
    AdBannerComponent,
    ItemContenidoComponent,
    FiltroBusquedaPipe,
    SuscriptorMainComponent,
    FinalizarFederacionComponent,
    AdministrarPlataformasComponent
  ],
  imports: [
    CommonModule,
    SuscriptorRoutingModule,
    ReactiveFormsModule
  ],
  providers: [
    VisualizacionService
  ]
})
export class SuscriptorModule { }
