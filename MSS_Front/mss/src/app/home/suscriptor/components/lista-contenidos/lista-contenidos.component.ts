import { Component, EventEmitter, Input, NgZone, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, ActivatedRouteSnapshot, NavigationStart, Router } from '@angular/router';
import { IContenido } from 'src/app/api/models/i-contenido';
import { IGeneroContenido } from 'src/app/api/models/i-genero-contenido';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';

@Component({
  selector: 'app-lista-contenidos',
  templateUrl: './lista-contenidos.component.html',
  styleUrls: ['./lista-contenidos.component.css']
})
export class ListaContenidosComponent implements OnInit{
  @Input() contenidos: IContenido[] = [];
  @Input() generos: IGeneroContenido[] = [];
  filterForm: FormGroup;
  contenidosFiltrados: IContenido[] = [];
  searchTerm: string = "";
  @Input() contenidoVisualizado: IContenido= {
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
  @Input() visualizando: boolean = false;
  @Output() visualizandoChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(private fb: FormBuilder, private _router: Router, private _ngZone: NgZone, private _msgSrv: MensajeService) {
    this.filterForm = this.fb.group({
      tipo: ['Todo'],
      genero: ['Todo'],
      searchTerm: [''],
    });

    this._router.events.subscribe({
      next: (event) => {
        if (event instanceof NavigationStart){
          this.filterForm.setValue({
            tipo: ['Todo'],
            genero: ['Todo'],
            searchTerm: ['']
          });
        }
      },
      error: (error) => {
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en login", text: error}), 0);
      }
    })
  }
  
  ngOnInit(): void {
    this.contenidosFiltrados = this.contenidos;
    console.log(this.contenidos);
  }

  filtrarTipoGenero(form: any) {
    // Lógica para aplicar los filtros a la lista de videos
    // Puedes acceder a los valores del formulario usando this.filterForm.value
    const tipo: string = form.tipo;
    const genero = form.genero;
    this.searchTerm = form.searchTerm;
    // Aplica los filtros según tus necesidades
    this.contenidosFiltrados = this.contenidos.filter((contenido) => {
      return (
        (tipo === 'Todo' || contenido.tipo_contenido === tipo || tipo.toLowerCase().includes(contenido.tipo_contenido.toLowerCase())) &&
        (genero === 'Todo' || contenido.genero === genero)
      );
    });
  }
}
