import { Component, EventEmitter, Input, NgZone, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, ActivatedRouteSnapshot, NavigationStart, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { IContenido } from 'src/app/api/models/i-contenido';
import { IContenidoXPlataforma } from 'src/app/api/models/i-contenido-x-plataforma';
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
  searchTerm: string = "";
  visualizando: boolean = false;
  private _subscription!: Subscription;

  constructor(private fb: FormBuilder, private _router: Router, private _ngZone: NgZone, private _msgSrv: MensajeService,private _vsServ: VisualizacionService) {
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
