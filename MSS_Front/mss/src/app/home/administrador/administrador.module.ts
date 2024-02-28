import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdministradorRoutingModule } from './administrador-routing.module';
import { AbmPlataformasComponent } from './components/abm-plataformas/abm-plataformas.component';
import { AbmPublicistasComponent } from './components/abm-publicistas/abm-publicistas.component';
import { AbmPublicidadesComponent } from './components/abm-publicidades/abm-publicidades.component';
import { AbmBannersComponent } from './components/abm-banners/abm-banners.component';
import { ModalPlataformaComponent } from './modals/modal-plataforma/modal-plataforma.component';
import { ModalPublicistaComponent } from './modals/modal-publicista/modal-publicista.component';
import { ModalPublicidadComponent } from './modals/modal-publicidad/modal-publicidad.component';
import { AdministradorMainComponent } from './components/administrador-main/administrador-main.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    AbmPlataformasComponent,
    AbmPublicistasComponent,
    AbmPublicidadesComponent,
    AbmBannersComponent,
    ModalPlataformaComponent,
    ModalPublicistaComponent,
    ModalPublicidadComponent,
    AdministradorMainComponent
  ],
  imports: [
    CommonModule,
    AdministradorRoutingModule,
    ReactiveFormsModule,
    NgbModule
  ]
})
export class AdministradorModule {
 }
