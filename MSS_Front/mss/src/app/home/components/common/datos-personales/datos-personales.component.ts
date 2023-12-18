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

  listarPlataformas(){
    this._rsService.obtenerListadoPlataformas({token_usuario: this.user.token}).subscribe({
      next: (respuesta: RespuestaBean) => {
        if (getCodigo(respuesta) == Codigo.OK){
          this.plataformas = JSON.parse(respuesta.body!);
        }
      },
      error: (error) => {

      }
    });
  }

  federar(id_plataforma: number){
    console.log(id_plataforma);
    this._rsService.iniciarFederacionPlataforma({id_plataforma: id_plataforma, 
                                                  token_suscriptor: this.user.token, 
                                                  url_retorno: 'http:localhost:8090/home/suscriptor/finalizarFederacion/'+id_plataforma}).subscribe({
      next: (respuesta: RespuestaBean) => {
        console.log(respuesta);
        if (getCodigo(respuesta) == Codigo.OK){
          console.log(JSON.parse(respuesta.body!));
          const url = JSON.parse(respuesta.body!).URL;
          console.log(url);
          window.location.href = url;
        }
      },
      error: (error) => {
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en login", text: error}), 0);
      }
    });
  }
}
