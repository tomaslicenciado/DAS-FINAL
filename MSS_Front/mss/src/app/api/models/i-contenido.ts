import { IActuacion } from "./i-actuacion";
import { IContenidoXPlataforma } from "./i-contenido-x-plataforma";
import { IDireccion } from "./i-direccion";

export interface IContenido {
    eidr_contenido: string,
    titulo: string,
    url_imagen: string,
    descripcion: string,
    fecha_estreno: Date,
    genero: string,
    tipo_contenido: string,
    pais: string,
    actuaciones: IActuacion[],
    direcciones: IDireccion[],
    cont_x_plataforma: IContenidoXPlataforma[]
}
