import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RespuestaBean } from 'src/app/api/models/respuesta-bean';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';
import { MssApiRestResourceService } from 'src/app/api/resources/mss-api-rest-resource.service';

@Component({
  selector: 'app-finalizar-federacion',
  templateUrl: './finalizar-federacion.component.html',
  styleUrls: ['./finalizar-federacion.component.css']
})
export class FinalizarFederacionComponent implements OnInit {
  private id_plataforma: number = 0;

  constructor(private _basicService: MssApiService, private _rsService: MssApiRestResourceService, private _route: ActivatedRoute){
    this._route.params.subscribe({
      next: (params) =>{
        this.id_plataforma = params['id'];
      }
    });
  }

  ngOnInit(): void {
    const user = this._basicService.getUser();
    this._rsService.finalizarFederacionPlataforma({token_suscriptor: user.token, id_plataforma: this.id_plataforma}).subscribe({
      next: (respuesta: RespuestaBean) => {
        console.log(respuesta);
      }
    })
  }

}
