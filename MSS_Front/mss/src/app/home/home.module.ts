import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { MenuBarComponent } from './components/common/menu-bar/menu-bar.component';
import { AdministradorModule } from './administrador/administrador.module';
import { SuscriptorModule } from './suscriptor/suscriptor.module';
import { PublicistaModule } from './publicista/publicista.module';
import { HomeComponent } from './components/common/home/home.component';
import { MssApiService } from '../api/resolvers/mss-api.service';
import { DatosPersonalesComponent } from './components/common/datos-personales/datos-personales.component';


@NgModule({
  declarations: [
    MenuBarComponent,
    HomeComponent,
    DatosPersonalesComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    AdministradorModule,
    SuscriptorModule,
    PublicistaModule
  ],
  providers: [
    MssApiService
  ]
})
export class HomeModule { }
