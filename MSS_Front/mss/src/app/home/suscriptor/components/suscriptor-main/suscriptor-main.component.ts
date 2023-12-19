import { Component, NgZone, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Codigo } from 'src/app/api/models/codigo';
import { IBanner } from 'src/app/api/models/i-banner';
import { IContenido } from 'src/app/api/models/i-contenido';
import { IContenidoXPlataforma } from 'src/app/api/models/i-contenido-x-plataforma';
import { IGeneroContenido } from 'src/app/api/models/i-genero-contenido';
import { IPlataforma } from 'src/app/api/models/i-plataforma';
import { IPublicacion } from 'src/app/api/models/i-publicacion';
import { RespuestaBean, getCodigo } from 'src/app/api/models/respuesta-bean';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';
import { MssApiRestResourceService } from 'src/app/api/resources/mss-api-rest-resource.service';
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
  masVistos: IContenido[] = [];
  destacados: IContenido[] = [];
  recientes: IContenido[] = [];
  plataformasNoFederadas: IPlataforma[] = []
  federando: boolean = false;

  constructor(private _router: Router, private _route: ActivatedRoute, private _ngZone: NgZone, 
          private _msgSrv: MensajeService, private _rsService: MssApiRestResourceService, private _mssSrv: MssApiService){}
  
  ngOnInit(): void {
    this._route.data.subscribe({
      next: (data) => {
        const respPublicaciones: RespuestaBean = data['publicaciones'];
        const respCatalogo: RespuestaBean = data['catalogo'];
        const respPlataformas: RespuestaBean = data['plataformas'];
        const respGeneros: RespuestaBean = data['generos'];
        const respMasvistos: RespuestaBean = data['masVistos'];
        if (getCodigo(respPublicaciones) == Codigo.OK){
          this.publicaciones = JSON.parse(respPublicaciones.body!);
        }
        if (getCodigo(respCatalogo) == Codigo.OK){
          this.catalogo = JSON.parse(respCatalogo.body!);
        }
        if (getCodigo(respPlataformas) == Codigo.OK){
          this.plataformas = JSON.parse(respPlataformas.body!);
          this.plataformasNoFederadas = this.plataformas;
        }
        if (getCodigo(respGeneros) == Codigo.OK){
          this.generos = JSON.parse(respGeneros.body!)
        }
        if (getCodigo(respMasvistos) == Codigo.OK){
          const eidrs: string[] = JSON.parse(respMasvistos.body!);
          this.masVistos = this.catalogo.filter((contenido) => {
            return eidrs.some((e) => {
              return contenido.eidr_contenido == e;
            })
          })
        }

        let idPlataformasFederadas: number[] = [];
        this.catalogo.forEach((contenido: IContenido) => {
          let destacados: IContenidoXPlataforma[] = [];
          let recientes: IContenidoXPlataforma[] = [];
          contenido.cont_x_plataforma.forEach((cxp: IContenidoXPlataforma)=>{
            cxp.url_icono_plataforma = this.plataformas.filter((p: IPlataforma) => {
              const coincidencia = p.id_plataforma == cxp.id_plataforma;
              if (coincidencia)
                idPlataformasFederadas.push(p.id_plataforma);
              return coincidencia;
            })[0].url_icono;
            if (cxp.destacado)
              destacados.push(cxp); 
                      
            if ((new Date().getTime() - new Date(cxp.fecha_carga).getTime()) / (1000*60*60*24) <= 15){
              recientes.push(cxp);
            }
          });
          
          if (destacados.length > 0){
            let copia = {...contenido}
            copia.cont_x_plataforma = destacados;
            this.destacados.push(copia);
          }
          if (recientes.length > 0){
            let copia = {...contenido}
            copia.cont_x_plataforma = recientes;
            this.recientes.push(copia);
          }
        })
        
        this.plataformasNoFederadas = this.plataformasNoFederadas.filter((plataforma) => {
          return !idPlataformasFederadas.some((i) => {return plataforma.id_plataforma == i})
        })
        
      },
      error: (error) => {
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en login", text: error}), 0);
      }
    });
  }

  listarPlataformas(){
    this.federando = !this.federando;
  }

  federar(id_plataforma: number){
    this.federando = false;
    this._rsService.iniciarFederacionPlataforma({id_plataforma: id_plataforma, 
                                                  token_suscriptor: this._mssSrv.getUser().token, 
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
