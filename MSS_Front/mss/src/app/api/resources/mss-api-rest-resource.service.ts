import { Injectable } from '@angular/core';
import { IResourceMethodObservable, Resource, ResourceAction, ResourceParams, ResourceRequestMethod, ResourceResponseBodyType } from '@ngx-resource/core';
import { environment } from 'src/environments/environment';
import { RespuestaBean } from '../models/respuesta-bean';

@Injectable()
@ResourceParams({
  pathPrefix: `${environment.apiUrl}`
})
export class MssApiRestResourceService extends Resource{

  constructor() { super(); }

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/iniciarSesion',
    responseBodyType: ResourceResponseBodyType.Json
  })
  iniciarSesion!: IResourceMethodObservable<{email: string, password: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/registrarSuscriptor',
    responseBodyType: ResourceResponseBodyType.Json
  })
  registrarSuscriptor!: IResourceMethodObservable<{email: string, password: string, nombres: string, apellidos: string, preferencias: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/iniciarFederacionPlataforma',
    responseBodyType: ResourceResponseBodyType.Json
  })
  iniciarFederacionPlataforma!: IResourceMethodObservable<{id_plataforma: number, token_suscriptor: string, url_retorno: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/finalizarFederacionPlataforma',
    responseBodyType: ResourceResponseBodyType.Json
  })
  finalizarFederacionPlataforma!: IResourceMethodObservable<{token_suscriptor: string, id_plataforma: number},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/desuscribirPlataforma',
    responseBodyType: ResourceResponseBodyType.Json
  })
  desuscribirPlataforma!: IResourceMethodObservable<{id_plataforma: number, token_suscriptor: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/obtenerContenido',
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtenerContenido!: IResourceMethodObservable<{token_suscriptor: string, id_plataforma: number, eidr_contenido: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/obtenerListadoGenerosContenido',
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtenerListadoGenerosContenido!: IResourceMethodObservable<{token_usuario: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/obtenerListadoPlataformas',
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtenerListadoPlataformas!: IResourceMethodObservable<{token_usuario: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/obtenerCatalogo',
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtenerCatalogo!: IResourceMethodObservable<{token_suscriptor: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/obtenerPublicidades',
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtenerPublicidades!: IResourceMethodObservable<{token_suscriptor: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/registrarVisualizacion',
    responseBodyType: ResourceResponseBodyType.Json
  })
  registrarVisualizacion!: IResourceMethodObservable<{token_suscriptor: string, id_plataforma: number, eidr_contenido: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/registrarAccesoPublicidad',
    responseBodyType: ResourceResponseBodyType.Json
  })
  registrarAccesoPublicidad!: IResourceMethodObservable<{token_suscriptor: string, id_publicidad: number},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/registrarNuevaPlataforma',
    responseBodyType: ResourceResponseBodyType.Json
  })
  registrarNuevaPlataforma!: IResourceMethodObservable<{token_usuario: string, nombre: string, url_conexion: string, url_icono: string, email: string,
                                                        id_tipo_servicio: number, token_servicio: string, tarifa_nuevos_viewers: number, tarifa_viewers_activos: number},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/actualizarPlataforma',
    responseBodyType: ResourceResponseBodyType.Json
  })
  actualizarPlataforma!: IResourceMethodObservable<{token_usuario: string, id_plataforma: number, nombre: string, url_conexion: string, url_icono: string, email: string,
                id_tipo_servicio: number, token_servicio: string, tarifa_nuevos_viewers: number, tarifa_viewers_activos: number},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/eliminarPlataforma',
    responseBodyType: ResourceResponseBodyType.Json
  })
  eliminarPlataforma!: IResourceMethodObservable<{token_usuario: string, id_plataforma: number},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/modificarBanner',
    responseBodyType: ResourceResponseBodyType.Json
  })
  modificarBanner!: IResourceMethodObservable<{token_usuario: string, banner_code: number, tarifa_base: number, tarifa_exclusividad: number},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/agregarPublicista',
    responseBodyType: ResourceResponseBodyType.Json
  })
  agregarPublicista!: IResourceMethodObservable<{token_usuario: string, nombre: string, razon_social: string, email: string, telefono: number, nombre_contacto: string,
                                                 url_conexion: string, token_servicio: string, id_tipo_servicio: number},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/modificarPublicista',
    responseBodyType: ResourceResponseBodyType.Json
  })
  modificarPublicista!: IResourceMethodObservable<{token_usuario: string, id_publicista: number, nombre: string, razon_social: string, email: string, telefono: number, 
                                                      nombre_contacto: string, id_tipo_servicio: number, url_conexion: string, token_servicio: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/eliminarPublicista',
    responseBodyType: ResourceResponseBodyType.Json
  })
  eliminarPublicista!: IResourceMethodObservable<{token_usuario: string, id_publicista: number},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/agregarPublicidad',
    responseBodyType: ResourceResponseBodyType.Json
  })
  agregarPublicidad!: IResourceMethodObservable<{token_usuario: string, id_publicista: number, banner_code: number, exclusiva: boolean, 
                                                fecha_inicio: string, fecha_fin: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/modificarPublicidad',
    responseBodyType: ResourceResponseBodyType.Json
  })
  modificarPublicidad!: IResourceMethodObservable<{token_usuario: string, id_publicidad: number, exclusiva: boolean, fecha_inicio: string, fecha_fin: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/eliminarPublicidad',
    responseBodyType: ResourceResponseBodyType.Json
  })
  eliminarPublicidad!: IResourceMethodObservable<{token_usuario: string, id_publicidad: number},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/obtenerFeesAPagar',
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtenerFeesAPagar!: IResourceMethodObservable<{token_usuario: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/modificarDatosPersonales',
    responseBodyType: ResourceResponseBodyType.Json
  })
  modificarDatosPersonales!: IResourceMethodObservable<{token_usuario: string, nombres: string, apellidos: string, password: string},RespuestaBean>
  
  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/obtenerListadoTiposServicio',
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtenerListadoTiposServicio!: IResourceMethodObservable<{token_usuario: string},RespuestaBean>
  
  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/obtenerListadoPublicistas',
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtenerListadoPublicistas!: IResourceMethodObservable<{token_usuario: string},RespuestaBean>
  
  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/obtenerListadoBanners',
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtenerListadoBanners!: IResourceMethodObservable<{token_usuario: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/finalizarFederacionesPendientes',
    responseBodyType: ResourceResponseBodyType.Json
  })
  finalizarFederacionesPendientes!: IResourceMethodObservable<{token_usuario: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/enviarFacturas',
    responseBodyType: ResourceResponseBodyType.Json
  })
  enviarFacturas!: IResourceMethodObservable<{token_usuario: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/enviarEstadisticas',
    responseBodyType: ResourceResponseBodyType.Json
  })
  enviarEstadisticas!: IResourceMethodObservable<{token_usuario: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/actualizarCatalogo',
    responseBodyType: ResourceResponseBodyType.Json
  })
  actualizarCatalogo!: IResourceMethodObservable<{token_usuario: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/actualizarPublicidades',
    responseBodyType: ResourceResponseBodyType.Json
  })
  actualizarPublicidades!: IResourceMethodObservable<{token_usuario: string},RespuestaBean>

  @ResourceAction({
    method: ResourceRequestMethod.Post,
    path: '/obtenerContenidosMasVistos',
    responseBodyType: ResourceResponseBodyType.Json
  })
  obtenerContenidosMasVistos!: IResourceMethodObservable<{token_usuario: string},RespuestaBean>
}
