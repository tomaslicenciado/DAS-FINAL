import { Injectable } from '@angular/core';
import { IPublicacion } from 'src/app/api/models/i-publicacion';

@Injectable({
  providedIn: 'root'
})
export class PublicidadesService {
  private _publicaciones: IPublicacion[] = [];
  private _lastUpdate: Date = new Date(Date.now());


  public SetPublicidades(publicaciones: IPublicacion[]) :void{
    this._publicaciones = publicaciones;
  }

  public GetPublicaciones(): IPublicacion[]{
    return this._publicaciones;
  }

  public PublicacionesCargadas(): boolean {
    return this._publicaciones.length > 0;
  }

  public MinutesFromLastUpdate(): number{
    return (Date.now() - this._lastUpdate.getTime())/60000;
  }

}
