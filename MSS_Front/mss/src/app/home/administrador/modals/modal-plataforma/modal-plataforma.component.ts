import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IPlataforma } from 'src/app/api/models/i-plataforma';

@Component({
  selector: 'app-modal-plataforma',
  templateUrl: './modal-plataforma.component.html',
  styleUrls: ['./modal-plataforma.component.css']
})
export class ModalPlataformaComponent {
  @Output() plataformaAgregada = new EventEmitter<IPlataforma>();
  modalTitle: string = 'Agregar Plataforma';
  plataforma: IPlataforma = { 
    id_plataforma: 0,
    nombre: '',
    url_icono: '',
    url_conexion: '',
    token_servicio: '',
    id_tipo_servicio: 0,
    tarifa_nuevo_viewer: 0,
    tarifa_viewer_activo: 0
  };
  formGorup: FormGroup;

  constructor(public activeModal: NgbActiveModal, private _fb: FormBuilder) {
    this.formGorup = this._fb.group({
      nombre: new FormControl([this.plataforma.nombre, Validators.required]),
      url_icono:  new FormControl(['', Validators.required]),
      url_conexión: new FormControl(['', Validators.required]),
      token_servicio: new FormControl(['', Validators.required]),
      id_tipo_servicio: new FormControl(['', Validators.required]),
      tarifa_nuevo_viewer: new FormControl(['', Validators.required]),
      tarifa_viewer_activo: new FormControl(['', Validators.required])
    });
  }

  guardarPlataforma(): void {
    // Puedes agregar lógica de validación aquí si es necesario
    // Enviar la nueva plataforma al componente padre
    this.plataformaAgregada.emit(this.plataforma);
    this.activeModal.close();
  }
}
