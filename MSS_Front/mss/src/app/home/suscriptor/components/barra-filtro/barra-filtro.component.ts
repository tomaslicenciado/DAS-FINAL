import { Component, EventEmitter, Input, NgZone, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { IGeneroContenido } from 'src/app/api/models/i-genero-contenido';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';

@Component({
  selector: 'app-barra-filtro',
  templateUrl: './barra-filtro.component.html',
  styleUrls: ['./barra-filtro.component.css']
})
export class BarraFiltroComponent implements OnInit {
  @Input() generos: IGeneroContenido[] = [];
  filterForm!: FormGroup;

  @Output() filterChange: EventEmitter<any> = new EventEmitter<any>();

  constructor(private fb: FormBuilder, private _ngZone: NgZone, private _msgSrv: MensajeService) {}

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      tipo: ['Todo'],
      genero: ['Todo'],
      searchTerm: [''],
    });

    this.filterForm.valueChanges.subscribe({
      next: (value) => {
        this.onFilterChange();
      },
      error: (error) => {
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en login", text: error}), 0);
      }
    });
  }

  onFilterChange() {
    this.filterChange.emit(this.filterForm.value);
  }
}
