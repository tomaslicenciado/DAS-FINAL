import { Injectable, OnInit } from '@angular/core';
import { Codigo } from 'src/app/api/models/codigo';
import { IPlataforma } from 'src/app/api/models/i-plataforma';
import { RespuestaBean, getCodigo } from 'src/app/api/models/respuesta-bean';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';
import { MssApiRestResourceService } from 'src/app/api/resources/mss-api-rest-resource.service';

@Injectable({
  providedIn: 'root'
})
export class SuscriptorService implements OnInit{
  plataformas: IPlataforma[] = [];
  plataformasNoFederadas: IPlataforma[] = [];
  idsPlataformasFederadas: number[] = [];

  constructor(private _rsService: MssApiRestResourceService, private _mssSrv: MssApiService) {
    this._rsService.obtenerListadoPlataformas({token_usuario: this._mssSrv.getUser().token}).subscribe({
      next: (respuesta: RespuestaBean) => {
        if (getCodigo(respuesta) == Codigo.OK){
          this.plataformas = JSON.parse(respuesta.body!);
          this.plataformasNoFederadas = this.actualizarListadoPlataformas();
        }
      }
    }); 
  }

  ngOnInit(): void {
  }

  actualizarListadoPlataformas(): IPlataforma[]{
    this.plataformasNoFederadas = this.plataformas.filter((plataforma) => {
      return !this.idsPlataformasFederadas.some((i) => {return plataforma.id_plataforma == i})
    })
    return this.plataformasNoFederadas;
  }
}
