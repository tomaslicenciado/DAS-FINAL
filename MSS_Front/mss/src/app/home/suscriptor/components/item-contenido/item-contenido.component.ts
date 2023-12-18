import { Component, ElementRef, HostListener, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IContenido } from 'src/app/api/models/i-contenido';
import { IContenidoXPlataforma } from 'src/app/api/models/i-contenido-x-plataforma';

@Component({
  selector: 'app-item-contenido',
  templateUrl: './item-contenido.component.html',
  styleUrls: ['./item-contenido.component.css']
})
export class ItemContenidoComponent implements OnInit{
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
  mostrarPlataformas: boolean = false;

  constructor(private _router: Router, private _el: ElementRef){}

  ngOnInit(): void {
    console.log(this.contenido)
  }

  verContenido(){
    this._router.navigate(['/visualizar-contenido'], { state: { contenido: this.contenido } });
  }

  togglePlataformas() {
    this.mostrarPlataformas = !this.mostrarPlataformas;
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
}
