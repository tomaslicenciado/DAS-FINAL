import { Component, ElementRef, EventEmitter, Input, NgZone, OnDestroy, OnInit, Output, QueryList, Renderer2, ViewChild, ViewChildren } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NavigationStart, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { IContenido } from 'src/app/api/models/i-contenido';
import { IGeneroContenido } from 'src/app/api/models/i-genero-contenido';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';
import { VisualizacionService } from '../../services/visualizacion.service';

@Component({
  selector: 'app-lista-contenidos',
  templateUrl: './lista-contenidos.component.html',
  styleUrls: ['./lista-contenidos.component.css']
})
export class ListaContenidosComponent implements OnInit, OnDestroy{
  @Input() contenidos: IContenido[] = [];
  @Input() generos: IGeneroContenido[] = [];
  filterForm: FormGroup;
  contenidosFiltrados: IContenido[] = [];
  @Input()contenidosDestacados: IContenido[] = [];
  @Input()contenidosRecientes: IContenido[] = [];
  @Input()contenidosMasVistos: IContenido[] = [];
  searchTerm: string = "";
  visualizando: boolean = false;
  private _subscription!: Subscription;
  filtrando: boolean = false;
  @ViewChildren('contenidoGrid') elementosDinamicos: QueryList<ElementRef> = new QueryList();

  constructor(private fb: FormBuilder, private _router: Router, private _ngZone: NgZone, private _msgSrv: MensajeService,private _vsServ: VisualizacionService,
    private renderer: Renderer2) {
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
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en listar contenidos", text: error}), 0);
      }
    })
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.contenidosFiltrados = this.contenidos;
    this._subscription = this._vsServ.getEstadoObservable().subscribe((vis) => {
      this.visualizando = vis;
    });
  }

  filtrarTipoGenero(form: any) {
    // Lógica para aplicar los filtros a la lista de videos
    // Puedes acceder a los valores del formulario usando this.filterForm.value
    this.filtrando = true;
    const tipo: string = form.tipo;
    const genero = form.genero;
    this.searchTerm = form.searchTerm;
    if (tipo === 'Todo' && genero === 'Todo' && this.searchTerm === "")
      this.filtrando = false;
    // Aplica los filtros según tus necesidades
    if (this.searchTerm === "*"){
      this.filtrando = true;
      this.searchTerm= "";
    }
    this.contenidosFiltrados = this.contenidos.filter((contenido) => {
      return (
        (tipo === 'Todo' || contenido.tipo_contenido === tipo) &&
        (genero === 'Todo' || contenido.genero === genero)
      );
    });
  }

  scrollLeft(index: number): void {
    this.desplazarLista(index, -1000);
  }

  scrollRight(index: number): void {
    this.desplazarLista(index, 1000);
  }

  private desplazarLista(index:number, px: number): void {
    this.elementosDinamicos.get(index)!.nativeElement.scrollLeft += px;
  }
}
