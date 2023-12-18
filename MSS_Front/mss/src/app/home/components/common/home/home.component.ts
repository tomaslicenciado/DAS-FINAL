import { Component, NgZone, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IUser } from 'src/app/api/models/i-user';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  user: IUser;

  constructor(private _route: ActivatedRoute, private _router: Router, private _ngZone: NgZone, private _msgSrv: MensajeService){
    this.user = {
      nombres: '',
      apellidos: '',
      token: '',
      id_nivel: 0,
      id_usuario: 0,
      nivel: '',
      validado: false
    }
  }

  ngOnInit(): void {
    this._route.data.subscribe({
      next: (data) =>{
        this.user = data["user"];
      },
      error: (error) => {
        this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en login", text: error}), 0);
      }
    });
  }
}
