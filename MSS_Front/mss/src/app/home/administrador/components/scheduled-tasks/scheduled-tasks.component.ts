import { Component, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { RespuestaBean } from 'src/app/api/models/respuesta-bean';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';
import { MssApiRestResourceService } from 'src/app/api/resources/mss-api-rest-resource.service';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';

@Component({
  selector: 'app-scheduled-tasks',
  templateUrl: './scheduled-tasks.component.html',
  styleUrls: ['./scheduled-tasks.component.css']
})
export class ScheduledTasksComponent {

  resultado: string | null = null;

  constructor(private _router: Router, private _apiService: MssApiRestResourceService, private _mssService: MssApiService,
          private _ngZone: NgZone, private _mensajeService: MensajeService){}

  volver(){
    this._router.navigate(['/administrador']);
  }

  actualizarCatalogo(){
    const token = this._mssService.getUser().token;
    this._apiService.actualizarCatalogo({token_usuario: token}).subscribe({
      next: (respuesta: RespuestaBean) => {
        this.resultado = respuesta.mensaje;
        console.log(respuesta);
      },
      error: (error) => {
        this._ngZone.run(() => this._mensajeService.showMessage({title: "Error al actualizar el catálogo", text: error}), 0);
      }
    });
  }

  actualizarPublicidades(){
    const token = this._mssService.getUser().token;
    this._apiService.actualizarPublicidades({token_usuario: token}).subscribe({
      next: (respuesta: RespuestaBean) => {
        this.resultado = respuesta.mensaje;
      },
      error: (error: Error) => {
        this._ngZone.run(() => this._mensajeService.showMessage({title: "Error en obtención de publicidades", text: error.message}), 0);
      }
    })
  }
  
  enviarEstadisticas(){}
  enviarFacturas(){}
  finalizarFederacionesPendientes(){}
}
