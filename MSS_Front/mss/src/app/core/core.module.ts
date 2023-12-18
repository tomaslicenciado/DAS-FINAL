import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoaderComponent } from './loader/component/loader/loader.component';
import { MensajeComponent } from './mensajes/component/mensaje/mensaje.component';
import { AppErrorHandler } from './errorHandler/app-error-handler';



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
    MensajeComponent
  ]
})
export class CoreModule { }
