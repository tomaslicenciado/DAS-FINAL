import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IMensaje } from '../../model/i-mensaje';
import { MensajeService } from '../../service/mensaje.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-mensaje',
  templateUrl: './mensaje.component.html',
  styleUrls: ['./mensaje.component.css']
})
export class MensajeComponent implements OnInit{
  mensaje: IMensaje | undefined;

  constructor(public activeModal: NgbActiveModal, private _service: MensajeService, private _router: Router) { }
  
  ngOnInit(): void {
    this.mensaje = this._service.mensaje;
  }

  cerrar(){
    this.activeModal.close();
    this._router.navigate(['/']);
  }
}
