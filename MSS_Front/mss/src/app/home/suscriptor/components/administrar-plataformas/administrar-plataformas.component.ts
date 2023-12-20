import { Component, NgZone, OnInit } from '@angular/core';
import { IPlataforma } from 'src/app/api/models/i-plataforma';
import { SuscriptorService } from '../../services/suscriptor.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { MssApiRestResourceService } from 'src/app/api/resources/mss-api-rest-resource.service';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';
import { RespuestaBean, getCodigo } from 'src/app/api/models/respuesta-bean';
import { Codigo } from 'src/app/api/models/codigo';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-administrar-plataformas',
  templateUrl: './administrar-plataformas.component.html',
  styleUrls: ['./administrar-plataformas.component.css']
})
export class AdministrarPlataformasComponent implements OnInit{
  plataformas: IPlataforma[] = []
  plataformasNoFederadas: IPlataforma[] = []

  constructor(private _sServ: SuscriptorService,public activeModal: NgbActiveModal, private _rsService: MssApiRestResourceService, 
          private _mssSrv: MssApiService, private _ngZone: NgZone, private _msgSrv: MensajeService, private _router: Router) {
    
  }

  ngOnInit(): void {
    this.plataformas = this._sServ.plataformas;
    this.plataformasNoFederadas = this._sServ.actualizarListadoPlataformas();
  }

  federar(plataforma: IPlataforma){
    const id_plataforma = plataforma.id_plataforma;
    this._rsService.iniciarFederacionPlataforma({id_plataforma: id_plataforma, 
                                                  token_suscriptor: this._mssSrv.getUser().token, 
                                                  url_retorno: 'http://localhost:8090/home/suscriptor/finalizarFederacion/'+id_plataforma}).subscribe({
      next: (respuesta: RespuestaBean) => {
        console.log(respuesta);
        if (getCodigo(respuesta) == Codigo.OK){
          const url = JSON.parse(respuesta.body!).URL;
          this._sServ.idsPlataformasFederadas.push(id_plataforma);
          window.location.href = url;
        }
      },
      error: (error) => {
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error al federar una plataforma", text: error}), 0);
      }
    });
  }

  plataformaFederada(p: IPlataforma): boolean {
    return !this.plataformasNoFederadas.some(pn => p.id_plataforma == pn.id_plataforma);
  }

  desuscribir(plataforma: IPlataforma){
    const id_plataforma = plataforma.id_plataforma;
    this._rsService.desuscribirPlataforma({id_plataforma: id_plataforma, token_suscriptor: this._mssSrv.getUser().token}).subscribe({
      next: (respuesta: RespuestaBean) => {
        console.log(respuesta);
        if (getCodigo(respuesta) == Codigo.OK){
          this._sServ.idsPlataformasFederadas = this._sServ.idsPlataformasFederadas.filter(i => i != id_plataforma);
          this.plataformasNoFederadas = this._sServ.actualizarListadoPlataformas();
        }
      },
      error: (error) => {
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error al desuscribir la plataforma", text: error}), 0);
      }
    });
  }

  cerrar(){
    this.activeModal.close();
    window.location.reload();
  }
}
