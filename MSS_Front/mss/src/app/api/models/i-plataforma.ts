export interface IPlataforma {
    id_plataforma: number,
    nombre: string,
    url_icono: string
    url_conexion: string,
    token_servicio: string,
    id_tipo_servicio: number,
    tarifa_nuevo_viewer: number,
    tarifa_viewer_activo: number
}
