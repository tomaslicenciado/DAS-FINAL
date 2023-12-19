import { Component, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { Codigo } from 'src/app/api/models/codigo';
import { IPlataforma } from 'src/app/api/models/i-plataforma';
import { IUser } from 'src/app/api/models/i-user';
import { RespuestaBean, getCodigo } from 'src/app/api/models/respuesta-bean';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';
import { MssApiRestResourceService } from 'src/app/api/resources/mss-api-rest-resource.service';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';

@Component({
  selector: 'app-datos-personales',
  templateUrl: './datos-personales.component.html',
  styleUrls: ['./datos-personales.component.css']
})
export class DatosPersonalesComponent {
  user: IUser = {
    id_usuario: 0,
    id_nivel: 0,
    nombres: '',
    apellidos: '',
    token: '',
    nivel: '',
    validado: false
  };
  plataformas: IPlataforma[] = [];

  ngOnInit(): void {
    this.user =  this._service.getUser();
  }

  constructor(private _router: Router, private _service: MssApiService, private _rsService: MssApiRestResourceService,
              private _ngZone: NgZone, private _msgSrv: MensajeService){}

  
}
