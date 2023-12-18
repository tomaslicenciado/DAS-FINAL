import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IMensaje } from '../../model/i-mensaje';
import { MensajeService } from '../../service/mensaje.service';

@Component({
  selector: 'app-mensaje',
  templateUrl: './mensaje.component.html',
  styleUrls: ['./mensaje.component.css']
})
export class MensajeComponent implements OnInit{
  mensaje: IMensaje | undefined;

  constructor(public activeModal: NgbActiveModal, private _service: MensajeService) { }
  
  ngOnInit(): void {
    this.mensaje = this._service.mensaje;
  }
}
