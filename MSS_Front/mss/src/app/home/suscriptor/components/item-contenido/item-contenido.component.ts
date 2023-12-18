import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { IContenido } from 'src/app/api/models/i-contenido';
import { IContenidoXPlataforma } from 'src/app/api/models/i-contenido-x-plataforma';

@Component({
  selector: 'app-item-contenido',
  templateUrl: './item-contenido.component.html',
  styleUrls: ['./item-contenido.component.css']
})
export class ItemContenidoComponent {
  @Input() contenido: IContenido = {
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

  constructor(private _router: Router){}

  verContenido(){
    this._router.navigate(['/visualizar-contenido'], { state: { contenido: this.contenido } });
  }
}
