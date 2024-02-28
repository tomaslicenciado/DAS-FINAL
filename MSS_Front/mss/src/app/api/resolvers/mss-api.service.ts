import { Injectable, NgZone, OnInit, inject } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveEnd, ResolveFn, Router, RouterStateSnapshot } from '@angular/router';
import { MssApiRestResourceService } from '../resources/mss-api-rest-resource.service';
import { IUser } from '../models/i-user';
import { Observable, catchError, delay, of, tap } from 'rxjs';
import { RespuestaBean, getCodigo } from '../models/respuesta-bean';
import { Codigo } from '../models/codigo';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';
import { PublicidadesService } from 'src/app/core/services/publicidades.service';

@Injectable({
  providedIn: 'root'
})
export class MssApiService implements OnInit{
  private _user: IUser | null = null;
  private _isLogged: boolean = false;

  constructor(private _service: MssApiRestResourceService,
              private _ngZone: NgZone,
              private _router: Router,
              private _mensajeService: MensajeService) {
    const lsUserSesion = sessionStorage.getItem("mssUser");
    if (lsUserSesion){
      const userSesion = JSON.parse(lsUserSesion);
      if (userSesion.expiry && new Date().getDate() < userSesion.expiry){
        this._isLogged = true;
        this._user = userSesion.usuario;
      }
      else{
        sessionStorage.removeItem("mssUser");
      }
    }
  }

  ngOnInit(): void {
    const lsUserSesion = sessionStorage.getItem("mssUser");
    if (lsUserSesion){
      const userSesion = JSON.parse(lsUserSesion);
      if (userSesion.expiry && new Date().getDate() < userSesion.expiry){
        this._isLogged = true;
        this._user = userSesion.usuario;
      }
      else{
        sessionStorage.removeItem("mssUser");
      }
    }
  }

  login(email: string, password: string): void {
    this._service.iniciarSesion({email: email, password: password}).subscribe({
      next: (respuesta: RespuestaBean) => {
        this._isLogged = true;
        this._user = JSON.parse(respuesta.body!);
        sessionStorage.setItem("mssUser",JSON.stringify({
          expiry: new Date().getTime() + 7200000,
          usuario: this._user
        }));
        this._router.navigateByUrl('/');
        window.location.reload();
      },
      error: (error: RespuestaBean) => {
        let e:RespuestaBean = JSON.parse(JSON.stringify(error.body!));
        this._ngZone.run(() => this._mensajeService.showMessage({title: "Login incorrecto", text: e.mensaje}), 0);
      }
    })
  }

  register(email: string, password: string, nombres: string, apellidos: string, preferencias: number[]){
    let prefStr = JSON.stringify(preferencias);
    this._service.registrarSuscriptor({email: email, password: password, preferencias: prefStr, nombres: nombres, apellidos: apellidos}).subscribe({
      next: (respuesta: RespuestaBean) => {
        this._isLogged = true;
        this._user = JSON.parse(respuesta.body!);
        sessionStorage.setItem("mssUser",JSON.stringify({
          expiry: new Date().getTime() + 7200000,
          usuario: this._user
        }));
        this._router.navigateByUrl('/');
      },
      error: (error: RespuestaBean) => {
        let e:RespuestaBean = JSON.parse(JSON.stringify(error.body!));
        this._ngZone.run(() => this._mensajeService.showMessage({title: "Error en registro", text: e.mensaje}), 0);
      }
    });
  }

  logout(){
    this._user = null;
    this._isLogged = false;
    sessionStorage.removeItem("mssUser");
    this._router.navigateByUrl('/auth/login');
  }

  getUser(): IUser {
    return this._user!;
  }

  isLogged(): boolean{
    return this._isLogged;
  }

  modificarBanner(token:string, bc: number, tb: number, te: number){
    this._service.modificarBanner({token_usuario: token, banner_code: bc, tarifa_base: tb, tarifa_exclusividad: te}).subscribe({
      next: (respuesta: RespuestaBean) => {
        if (getCodigo(respuesta) != Codigo.OK){
          //Mensaje error
        }
      },
      error: (error: RespuestaBean) => {
        let e: RespuestaBean = JSON.parse(JSON.stringify(error.body!));
        this._ngZone.run(() => this._mensajeService.showMessage({title: "Error en login", text: e.mensaje}), 0);
      }
    });
  }

  convertirFormatoFecha(fechaString: string): string {
    const partes = fechaString.split('-');
    if (partes.length === 3) {
      const [anio, mes, dia] = partes;
      return `${dia}-${mes}-${anio}`;
    } else {
      return fechaString;
    }
  }
}

export const PublicacionesResolver: ResolveFn<Observable<RespuestaBean>> | Observable<RespuestaBean> = (_route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const publicacionesService = inject(PublicidadesService)
  if (publicacionesService.PublicacionesCargadas() && publicacionesService.MinutesFromLastUpdate() < 60){
    let resp: RespuestaBean = {status: Codigo.OK, body: JSON.stringify(publicacionesService.GetPublicaciones()), mensaje: ""};
    return of(resp);
  }
  else{
    const userService = inject(MssApiService);
    const user = userService.getUser();
    console.log(user.token);
    return inject(MssApiRestResourceService).obtenerPublicidades({token_suscriptor: user.token}).pipe(
      tap({
        next: (respuesta: RespuestaBean) => {
          publicacionesService.SetPublicidades(JSON.parse(respuesta.body!))
        },
        error: (error: RespuestaBean) => {
          let e:RespuestaBean = JSON.parse(JSON.stringify(error.body!));
          inject(NgZone).run(() => inject(MensajeService).showMessage({title: "Error al obtener publicidades", text: e.mensaje}), 0);
          return of();
        }
      })
    );
  }
}

export const UserResolver: ResolveFn<IUser> = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const userService = inject(MssApiService);

  return userService.getUser();
}

export const BannersResolver: ResolveFn<Observable<RespuestaBean>> = (_route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const userService = inject(MssApiService);
  const user = userService.getUser();

  return inject(MssApiRestResourceService).obtenerListadoBanners({token_usuario: user.token});
}

export const CatalogoResolver: ResolveFn<Observable<RespuestaBean>> = (_route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const userService = inject(MssApiService);
  const user = userService.getUser();

  return inject(MssApiRestResourceService).obtenerCatalogo({token_suscriptor: user.token!});
}

export const PlataformasResolver: ResolveFn<Observable<RespuestaBean>> = (_route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const userService = inject(MssApiService);
  const user = userService.getUser();

  return inject(MssApiRestResourceService).obtenerListadoPlataformas({token_usuario: user.token!});
}

export const GenerosResolver: ResolveFn<Observable<RespuestaBean>> = (_route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const userService = inject(MssApiService);
  const user = userService.getUser();

  return inject(MssApiRestResourceService).obtenerListadoGenerosContenido({token_usuario: user.token!});
}

export const PublicistasResolver: ResolveFn<Observable<RespuestaBean>> = (_route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const userService = inject(MssApiService);
  const user = userService.getUser();

  return inject(MssApiRestResourceService).obtenerListadoPublicistas({token_usuario: user.token!});
}
