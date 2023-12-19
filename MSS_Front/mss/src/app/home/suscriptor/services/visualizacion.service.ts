import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { IContenido } from 'src/app/api/models/i-contenido';

@Injectable({
  providedIn: 'root'
})
export class VisualizacionService {
  contenido: IContenido = {
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
  };
  idPlataforma: number = 0;
  url: string = '';
  private _subjectViendo = new Subject<boolean>();

  public getEstadoObservable(): Observable<boolean> {
    return this._subjectViendo.asObservable();
  }

  constructor() { }

  public visualizar(contenido: IContenido){
    this.contenido = contenido;
    this._subjectViendo.next(true);
  }

  public detener(){
    this._subjectViendo.next(false);
  }
}
