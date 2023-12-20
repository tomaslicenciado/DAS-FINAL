import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IUser } from 'src/app/api/models/i-user';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';
import { DatosPersonalesComponent } from '../datos-personales/datos-personales.component';

@Component({
  selector: 'app-menu-bar',
  templateUrl: './menu-bar.component.html',
  styleUrls: ['./menu-bar.component.css']
})
export class MenuBarComponent implements OnInit{
  opcionesMenu: any[] = []
  @Input() user: IUser = {
    id_usuario: 0,
    id_nivel: 0,
    nombres: '',
    apellidos: '',
    token: '',
    nivel: '',
    validado: false
  };
  isReady: boolean = false;

  ngOnInit(): void {
      this.isReady = true;
  }

  constructor(private _router: Router, private _service: MssApiService, private _modal: NgbModal){}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['user'] && changes['user'].currentValue) {
      this.user = changes['user'].currentValue;
      this.generarOpciones();
    }
  }

  generarOpciones(){
    switch (this.user.id_nivel){
      case 3:
        this.opcionesMenu.push({ref: '/administrador/abm-banners', titulo: 'Banners'})
        this.opcionesMenu.push({ref: '/administrador/abm-plataformas', titulo: 'Plataformas'})
        this.opcionesMenu.push({ref: '/administrador/abm-publicidades', titulo: 'Publicidades'})
        this.opcionesMenu.push({ref: '/administrador/abm-publicistas', titulo: 'Publicistas'})
        this.opcionesMenu.push({ref: '/administrador/scheduled-tasks', titulo: 'Scheduled Tasks'})
        break;
    }
  }

  modificarDatos(){
    this._modal.open(DatosPersonalesComponent, {size: 'lg'});
  }

  logout(){
    this._service.logout();
  }
}
