import { INJECTOR, Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IMensaje } from '../model/i-mensaje';
import { MensajeComponent } from '../component/mensaje/mensaje.component';

@Injectable({
  providedIn: 'root'
})
export class MensajeService {
  mensaje: IMensaje;

  constructor(private _modal: NgbModal) { 
    this.mensaje = { text: ''}
  }

  public showMessage(message: IMensaje): void {
    this.mensaje = message;
    const modalRef = this._modal.open(MensajeComponent);
  }
}
