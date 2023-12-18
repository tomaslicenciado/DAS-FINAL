import { Component, NgZone, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Codigo } from 'src/app/api/models/codigo';
import { IBanner } from 'src/app/api/models/i-banner';
import { IContenido } from 'src/app/api/models/i-contenido';
import { IGeneroContenido } from 'src/app/api/models/i-genero-contenido';
import { IPlataforma } from 'src/app/api/models/i-plataforma';
import { IPublicacion } from 'src/app/api/models/i-publicacion';
import { RespuestaBean, getCodigo } from 'src/app/api/models/respuesta-bean';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';

@Component({
  selector: 'app-suscriptor-main',
  templateUrl: './suscriptor-main.component.html',
  styleUrls: ['./suscriptor-main.component.css']
})
export class SuscriptorMainComponent implements OnInit{
  publicaciones: IPublicacion[] = [];
  catalogo: IContenido[] = [];
  plataformas: IPlataforma[] = [];
  generos: IGeneroContenido[] = [];
  banners: IBanner[] = [];

  constructor(private _router: Router, private _route: ActivatedRoute, private _ngZone: NgZone, private _msgSrv: MensajeService){}
  
  ngOnInit(): void {
    this._route.data.subscribe({
      next: (data) => {
        const respPublicaciones: RespuestaBean = data['publicaciones'];
        const respCatalogo: RespuestaBean = data['catalogo'];
        const respPlataformas: RespuestaBean = data['plataformas'];
        const respGeneros: RespuestaBean = data['generos'];
        const respBanners: RespuestaBean = data['banners'];
        if (getCodigo(respBanners) == Codigo.OK){
          this.banners = JSON.parse(respBanners.body!);
        }
        if (getCodigo(respPublicaciones) == Codigo.OK){
          this.publicaciones = JSON.parse(respPublicaciones.body!);
        }
        if (getCodigo(respCatalogo) == Codigo.OK){
          this.catalogo = JSON.parse(respCatalogo.body!);
        }
        if (getCodigo(respPlataformas) == Codigo.OK){
          this.plataformas = JSON.parse(respPlataformas.body!);
        }
        if (getCodigo(respGeneros) == Codigo.OK){
          this.generos = JSON.parse(respGeneros.body!)
        }
      },
      error: (error) => {
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en login", text: error}), 0);
      }
    });
  }
}
