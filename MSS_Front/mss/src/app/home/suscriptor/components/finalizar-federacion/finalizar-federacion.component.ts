import { Component, NgZone, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Codigo } from 'src/app/api/models/codigo';
import { RespuestaBean, getCodigo } from 'src/app/api/models/respuesta-bean';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';
import { MssApiRestResourceService } from 'src/app/api/resources/mss-api-rest-resource.service';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';

@Component({
  template: ""
})
export class FinalizarFederacionComponent implements OnInit {
  private id_plataforma: number = 0;

  constructor(private _basicService: MssApiService, private _rsService: MssApiRestResourceService, private _route: ActivatedRoute, private _router: Router,
        private _ngZone: NgZone, private _msgSrv: MensajeService){
    this._route.params.subscribe({
      next: (params) =>{
        this.id_plataforma = params['id'];
      }
    });
  }

  ngOnInit(): void {
    const user = this._basicService.getUser();
    this._rsService.finalizarFederacionPlataforma({token_suscriptor: user.token, id_plataforma: this.id_plataforma}).subscribe({
      next: (respuesta: RespuestaBean) => {
          this._router.navigate(['/']);
      },
      error: (error: RespuestaBean) => {
        let e: RespuestaBean = JSON.parse(JSON.stringify(error.body!));
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error al finalizar la federación", text: e.mensaje}), 0);
      }
    })
  }

}
