import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoaderComponent } from './loader/component/loader/loader.component';
import { MensajeComponent } from './mensajes/component/mensaje/mensaje.component';
import { MssApiRestResourceService } from '../api/resources/mss-api-rest-resource.service';



@NgModule({
  declarations: [
    LoaderComponent,
    MensajeComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    LoaderComponent,
    MensajeComponent,
  ],
  providers: [
    MssApiRestResourceService
  ]
})
export class CoreModule { }
