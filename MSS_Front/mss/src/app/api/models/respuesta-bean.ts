import { Codigo } from "./codigo";

export interface RespuestaBean {
    status: Codigo,
    mensaje: string,
    body?: string
}

export function getCodigo(respuesta: RespuestaBean): Codigo{
    switch (respuesta.status.toString()){
        case "OK":
            return Codigo.OK;
        case "CREADO":
            return Codigo.CREADO;
        case "ERROR":
            return Codigo.ERROR;
        case "NO_AUTORIZADO":
            return Codigo.NO_AUTORIZADO;
        case "NO_ENCONTRADO":
            return Codigo.NO_ENCONTRADO;
        default:
            return Codigo.ERROR;
    }
}