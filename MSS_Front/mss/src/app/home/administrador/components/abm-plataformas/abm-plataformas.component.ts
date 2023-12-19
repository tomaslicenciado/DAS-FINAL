import { Component, NgZone, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IPlataforma } from 'src/app/api/models/i-plataforma';
import { RespuestaBean, getCodigo } from 'src/app/api/models/respuesta-bean';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';
import { ModalPlataformaComponent } from '../../modals/modal-plataforma/modal-plataforma.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Codigo } from 'src/app/api/models/codigo';

@Component({
  selector: 'app-abm-plataformas',
  templateUrl: './abm-plataformas.component.html',
  styleUrls: ['./abm-plataformas.component.css']
})
export class AbmPlataformasComponent implements OnInit{
  plataformas: IPlataforma[] = [];

  constructor(private _route: ActivatedRoute, private _ngZone: NgZone, private _msgSrv: MensajeService, private modalService: NgbModal,
              private _router: Router) {
    
  }

  ngOnInit(): void {
    this._route.data.subscribe({
      next: (data) => {
        const respuesta: RespuestaBean = data['plataformas'];
        if (getCodigo(respuesta) == Codigo.OK){
          this.plataformas = JSON.parse(respuesta.body!);
        }
      },
      error: (error) => {
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en la obtención de plataformas", text: error}), 0);
      }
    });
  }

  editarPlataforma(plataforma: IPlataforma): void {
    const modalRef = this.modalService.open(ModalPlataformaComponent, { size: 'lg' });
    modalRef.componentInstance.modalTitle = 'Editar Plataforma';
    modalRef.componentInstance.plataforma = plataforma;
  }

  eliminarPlataforma(idPlataforma: number): void {
    // Lógica para eliminar la plataforma
    console.log(`Eliminar plataforma con ID: ${idPlataforma}`);
  }
  
  agregarPlataforma(): void {
    const modalRef = this.modalService.open(ModalPlataformaComponent, { size: 'lg' });
    modalRef.componentInstance.modalTitle = 'Agregar Nueva Plataforma';
  }

  // Método invocado cuando se agrega una plataforma desde el modal
  agregarNuevaPlataforma(nuevaPlataforma: IPlataforma): void {
    // Agregar lógica para manejar la nueva plataforma
    console.log('Plataforma agregada:', nuevaPlataforma);
    // Puedes realizar una llamada al servicio para actualizar la lista de plataformas
  }
  
  volver(){
    this._router.navigate(['/administrador']);
  }
}
