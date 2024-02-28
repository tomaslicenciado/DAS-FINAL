import { Component, ElementRef, EventEmitter, HostListener, Input, NgZone, OnDestroy, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IContenido } from 'src/app/api/models/i-contenido';
import { RespuestaBean, getCodigo } from 'src/app/api/models/respuesta-bean';
import { MssApiRestResourceService } from 'src/app/api/resources/mss-api-rest-resource.service';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';
import { Codigo } from 'src/app/api/models/codigo';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { IActuacion } from 'src/app/api/models/i-actuacion';
import { IDireccion } from 'src/app/api/models/i-direccion';
import { VisualizacionService } from '../../services/visualizacion.service';

@Component({
  selector: 'app-visualizar-contenido',
  templateUrl: './visualizar-contenido.component.html',
  styleUrls: ['./visualizar-contenido.component.css']
})
export class VisualizarContenidoComponent implements OnInit, OnDestroy{
  contenido: IContenido;
  reproducir: boolean = false;
  contenidoUrl: SafeResourceUrl = "";
  idPlataforma: number = 0;

  constructor(private _mssService: MssApiRestResourceService, private _ngZone: NgZone, private _msgSrv: MensajeService,
            private _router: Router, private _route: ActivatedRoute, private _el: ElementRef, private _vsServ: VisualizacionService,
            private _apiService: MssApiService, private sanitizer: DomSanitizer){
    this.contenido = {
      url_imagen: "",
      tipo_contenido: "",
      actuaciones: [],
      cont_x_plataforma: [],
      descripcion: "",
      direcciones: [],
      eidr_contenido: "",
      fecha_estreno: new Date,
      genero: "",
      pais: "",
      titulo: ""
    }
  }
  mostrarPlataformas: boolean = false;

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
    this.contenido = this._vsServ.contenido;
  }

  toggleReproducir(id_p: number) {
    this._mssService.obtenerContenido({token_suscriptor: this._apiService.getUser().token, id_plataforma: id_p, eidr_contenido: this.contenido.eidr_contenido}).subscribe({
      next: (respuesta: RespuestaBean) => {
        const urls = JSON.parse(respuesta.body!);
        this.contenidoUrl = this.sanitizer.bypassSecurityTrustResourceUrl(urls['url1']+"&autoplay=1");
        this.reproducir = !this.reproducir;
      },
      error: (error: RespuestaBean) => {
        let e: RespuestaBean = JSON.parse(JSON.stringify(error.body!));
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en visualización de contenido", text: e.mensaje}), 0);
      }
    });

    this._mssService.registrarVisualizacion({token_suscriptor: this._apiService.getUser().token,id_plataforma: id_p, eidr_contenido: this.contenido.eidr_contenido}).subscribe({
      next: (respuesta: RespuestaBean) => {
        console.log(respuesta);
      },
      error: (error: RespuestaBean) => {
        let e: RespuestaBean = JSON.parse(JSON.stringify(error.body!));
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en registro de visualización", text: e.mensaje}), 0);
      }
    });
  }

  volver(){
    this._vsServ.detener();
    window.location.reload();
  }

  seleccionarPlataforma(idPlataforma: number) {
    this.idPlataforma = idPlataforma;
  }

  @HostListener('document:click', ['$event'])
  handleDocumentClick(event: Event) {
     if (!this._el.nativeElement.contains(event.target)) {
        this.mostrarPlataformas = false;
     }
  }

  @HostListener('document:mouseover', ['$event'])
   handleDocumentMouseOver(event: Event) {
      if (this.mostrarPlataformas && !this._el.nativeElement.contains(event.target)) {
         this.mostrarPlataformas = false;
      }
   }

   convertirFormatoFecha(fechaString: string): string {
    const partes = fechaString.split('-');
    if (partes.length === 3) {
      const [anio, mes, dia] = partes;
      return `${dia}-${mes}-${anio}`;
    } else {
      // Manejo de error o devolución de la fecha original si no tiene el formato esperado
      return fechaString;
    }
  }

  obtenerActuaciones(contenido: IContenido): string {
    const actuaciones: IActuacion[] = contenido.actuaciones;
    return actuaciones.map((actuacion) => {return actuacion.nombres + " " + actuacion.apellidos}).join(", ");
  }

  obtenerDirecciones(contenido: IContenido): string {
    console.log(this.contenido);

    const direcciones: IDireccion[] = contenido.direcciones;
    return direcciones.map((direccion) => {return direccion.nombres + " " + direccion.apellidos}).join(", ");
  }
}
