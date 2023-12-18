import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { IUser } from 'src/app/api/models/i-user';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';

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

  constructor(private _router: Router, private _service: MssApiService){}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['user'] && changes['user'].currentValue) {
      // Se ejecutará cada vez que cambie la propiedad 'user'
      // Accede a changes.user.currentValue para obtener el nuevo valor del usuario
      this.user = changes['user'].currentValue;
      
      // Realiza las acciones necesarias basadas en el nuevo usuario
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
      case 1:
        this.opcionesMenu.push({ref: '/suscriptor', titulo: 'Peliculas'});
    }
  }

  modificarDatos(){}

  logout(){
    this._service.logout();
  }
}
