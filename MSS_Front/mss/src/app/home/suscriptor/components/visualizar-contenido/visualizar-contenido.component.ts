import { Component, Input, NgZone, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IActuacion } from 'src/app/api/models/i-actuacion';
import { IContenido } from 'src/app/api/models/i-contenido';
import { IDireccion } from 'src/app/api/models/i-direccion';
import { RespuestaBean } from 'src/app/api/models/respuesta-bean';
import { MssApiRestResourceService } from 'src/app/api/resources/mss-api-rest-resource.service';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';

@Component({
  selector: 'app-visualizar-contenido',
  templateUrl: './visualizar-contenido.component.html',
  styleUrls: ['./visualizar-contenido.component.css']
})
export class VisualizarContenidoComponent implements OnInit{
  @Input() contenido: IContenido;
  reproducir: boolean = false;
  contenidoUrl: string = "";
  idPlataforma: number = 0;

  constructor(private _mssService: MssApiRestResourceService, private _ngZone: NgZone, private _msgSrv: MensajeService,
            private _router: Router, private _route: ActivatedRoute){
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

  ngOnInit(): void {
    console.log(this.contenido)
  }

  get protagonistas(): string {
    return ""
    //return this.contenido.actuaciones.map((actuacion: IActuacion) => actuacion.actor).join(', ');
  }

  // Calcula la lista de direcciones como una cadena
  get direcciones(): string {
    return ""
    //return this.contenido.direcciones.map((direccion: IDireccion) => direccion.director).join(', ');
  }

  // Calcula la fecha de estreno en formato legible
  get fechaEstreno(): string {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return ""
    //return new Date(this.contenido.fecha_estreno).toLocaleDateString('es-ES', options);
  }

  toggleReproducir() {
    this._mssService.obtenerContenido().subscribe({
      next: (respuesta: RespuestaBean) => {
        //procesar el video
      },
      error: (error) => {
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en login", text: error}), 0);
      }
    });
    this.reproducir = !this.reproducir;
  }

  volver(){}

  seleccionarPlataforma(idPlataforma: number) {
    this.idPlataforma = idPlataforma;
  }
}
