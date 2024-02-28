import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { IPublicacion } from 'src/app/api/models/i-publicacion';
import { Subscription } from 'rxjs';
import { MssApiRestResourceService } from 'src/app/api/resources/mss-api-rest-resource.service';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';
import { RespuestaBean } from 'src/app/api/models/respuesta-bean';
import { VisualizacionService } from '../../services/visualizacion.service';

@Component({
  selector: 'app-ad-banner',
  templateUrl: './ad-banner.component.html',
  styleUrls: ['./ad-banner.component.css']
})
export class AdBannerComponent implements OnInit, OnDestroy{
  @Input() publicacion: IPublicacion = {
    banner_code: 0,
    url_contenido: "",
    url_imagen: "",
    codigo_unico_id: 0
  };
  private _subscription!: Subscription;
  visualizando: boolean = false;

  constructor(private _vsServ: VisualizacionService, private _rsService: MssApiRestResourceService, private _mssServ: MssApiService) {

  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  ngOnInit(): void {
    this._subscription = this._vsServ.getEstadoObservable().subscribe((vis: boolean) => {
      this.visualizando = vis;
    })
  }

  getBannerStyle(): any {
    // Lógica para determinar el estilo del banner según el banner_code
    let style: any = {};

    if (this.publicacion.banner_code.toString().startsWith('1')) {
      style = {
        'width': '14%',
        'height': '90%',
        'position': 'fixed',
        'top': '9%',
        'left': '9px',
        'z-index': '1000'
      };
    } else if (this.publicacion.banner_code.toString().startsWith('2')) {
      style = {
        'width': '14%',
        'height': '90%',
        'position': 'fixed',
        'top': '9%',
        'right': '9px',
        'z-index': '1000'
      };
    } else if (this.publicacion.banner_code.toString().startsWith('3')) {
      style = {
        'width': '70%',
        'height': '14%',
        'position': 'fixed',
        'bottom': '9px',
        'left': '15%',
        'z-index': '1000'
      };
    }

    return style;
  }

  registrarAcceso(){
    this._rsService.registrarAccesoPublicidad({token_suscriptor: this._mssServ.getUser().token, id_publicidad: this.publicacion.codigo_unico_id}).subscribe({
      next: (respuesta: RespuestaBean) => {
        console.log(respuesta);
      }
    });
  }

  getClaseOculta(): string[] {
    if (this.publicacion.url_contenido == "" || this.publicacion.url_imagen == ""
      || this.publicacion.url_contenido == null || this.publicacion.url_imagen == null)
      return ['d-none']
    return ['ad'];
  }
}
