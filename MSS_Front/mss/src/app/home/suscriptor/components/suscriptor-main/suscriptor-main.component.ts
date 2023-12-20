import { Component, NgZone, OnDestroy, OnInit } from '@angular/core';
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
import { Subscription } from 'rxjs';
import { VisualizacionService } from '../../services/visualizacion.service';
import { IUser } from 'src/app/api/models/i-user';
import { SuscriptorService } from '../../services/suscriptor.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AdministrarPlataformasComponent } from '../administrar-plataformas/administrar-plataformas.component';

@Component({
  selector: 'app-suscriptor-main',
  templateUrl: './suscriptor-main.component.html',
  styleUrls: ['./suscriptor-main.component.css']
})
export class SuscriptorMainComponent implements OnInit, OnDestroy{
  publicaciones: IPublicacion[] = [];
  catalogo: IContenido[] = [];
  plataformas: IPlataforma[] = [];
  generos: IGeneroContenido[] = [];
  masVistos: IContenido[] = [];
  destacados: IContenido[] = [];
  recientes: IContenido[] = [];
  federando: boolean = false;
  visualizando: boolean = false;
  private _subscription!: Subscription;
  private user: IUser = {
    id_usuario: 0,
    id_nivel: 0,
    nombres: '',
    apellidos: '',
    token: '',
    nivel: '',
    validado: false
  };

  constructor(private _router: Router, private _route: ActivatedRoute, private _ngZone: NgZone, 
          private _msgSrv: MensajeService, private _rsService: MssApiRestResourceService, private _mssSrv: MssApiService,
          private _vsService: VisualizacionService, private _sServ: SuscriptorService,private _modal: NgbModal){}
  
  ngOnInit(): void {
    this.user = this._mssSrv.getUser();
    this._route.data.subscribe({
      next: (data) => {
        const respPublicaciones: RespuestaBean = data['publicaciones'];
        const respCatalogo: RespuestaBean = data['catalogo'];
        const respPlataformas: RespuestaBean = data['plataformas'];
        const respGeneros: RespuestaBean = data['generos'];
        if (getCodigo(respPublicaciones) == Codigo.OK){
          this.publicaciones = JSON.parse(respPublicaciones.body!);
        }else{
          this._ngZone.run(() => this._msgSrv.showMessage({title: respPublicaciones.body!, text: respPublicaciones.mensaje, num: getCodigo(respPublicaciones)}), 0);
          this._router.navigate(['/home/suscriptor']);
        }
        if (getCodigo(respCatalogo) == Codigo.OK){
          this.catalogo = JSON.parse(respCatalogo.body!);
        }else{
          this._ngZone.run(() => this._msgSrv.showMessage({title: respCatalogo.body!, text: respCatalogo.mensaje, num: getCodigo(respCatalogo)}), 0);
          this._router.navigate(['/home/suscriptor']);
        }
        if (getCodigo(respPlataformas) == Codigo.OK){
          this.plataformas = JSON.parse(respPlataformas.body!);
        }else{
          this._ngZone.run(() => this._msgSrv.showMessage({title: respPlataformas.body!, text: respPlataformas.mensaje, num: getCodigo(respPlataformas)}), 0);
          this._router.navigate(['/home/suscriptor']);
        }
        if (getCodigo(respGeneros) == Codigo.OK){
          this.generos = JSON.parse(respGeneros.body!)
        }else{
          this._ngZone.run(() => this._msgSrv.showMessage({title: respGeneros.body!, text: respGeneros.mensaje, num: getCodigo(respGeneros)}), 0);
          this._router.navigate(['/home/suscriptor']);
        }

        if (this.catalogo.length > 0){

          let idPlataformasFederadas: number[] = [];
          this.catalogo.forEach((contenido: IContenido) => {
            let destacados: IContenidoXPlataforma[] = [];
            let recientes: IContenidoXPlataforma[] = [];
            if (contenido.cont_x_plataforma){
              contenido.cont_x_plataforma.forEach((cxp: IContenidoXPlataforma)=>{
                cxp.url_icono_plataforma = this.plataformas.filter((p: IPlataforma) => {
                  const coincidencia = p.id_plataforma == cxp.id_plataforma;
                  if (coincidencia)
                    idPlataformasFederadas.push(p.id_plataforma);
                  return coincidencia;
                })[0].url_icono;
                cxp.nombre_plataforma = this.plataformas.filter((p: IPlataforma) => {return p.id_plataforma == cxp.id_plataforma;})[0].nombre;
                if (cxp.destacado)
                  destacados.push(cxp); 
                          
                if ((new Date().getTime() - new Date(cxp.fecha_carga).getTime()) / (1000*60*60*24) <= 15){
                  recientes.push(cxp);
                }
              });
            }
            this._sServ.idsPlataformasFederadas = idPlataformasFederadas;
            
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
          });

        }else{
            this._ngZone.run(() => this._msgSrv.showMessage({title: "Error al procesar datos", 
                text: "No se pueden procesar los datos de contenido dado que no se ha cargado correctamente el catálogo", num: 500}), 0);
            this._router.navigate(['/home/suscriptor']);
          }
        
      },
      error: (error) => {
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en suscriptor home", text: error}), 0);
      }
    });

    this._subscription = this._vsService.getEstadoObservable().subscribe((v) => {
      this.visualizando = v;
    });

    this._rsService.obtenerContenidosMasVistos({token_suscriptor: this.user.token}).subscribe({
      next: (respMasvistos: RespuestaBean) => {
        if (getCodigo(respMasvistos) == Codigo.OK){
          const eidrs: string[] = JSON.parse(respMasvistos.body!);
          this.masVistos = this.catalogo.filter((contenido) => {
            return eidrs.some((e) => {
              return contenido.eidr_contenido == e;
            })
          })
        }else{
          this._ngZone.run(() => this._msgSrv.showMessage({title: respMasvistos.body!, text: respMasvistos.mensaje, num: getCodigo(respMasvistos)}), 0);
          this._router.navigate(['/home/suscriptor']);
        }
      }
    });
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  listarPlataformas(){
    this._modal.open(AdministrarPlataformasComponent);
  }
}
