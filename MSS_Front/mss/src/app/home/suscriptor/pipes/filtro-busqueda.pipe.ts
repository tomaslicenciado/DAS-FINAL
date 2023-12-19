import { Pipe, PipeTransform } from '@angular/core';
import { IActuacion } from 'src/app/api/models/i-actuacion';
import { IContenido } from 'src/app/api/models/i-contenido';

@Pipe({
  name: 'filtroBusqueda'
})
export class FiltroBusquedaPipe implements PipeTransform {

  transform(contenidos: IContenido[], searchTerm: string): IContenido[] {
    if (!contenidos || !searchTerm || searchTerm === "") {
      return contenidos;
    }
    searchTerm = searchTerm.toLowerCase();

    return contenidos.filter(contenido => {
      const regEx = new RegExp(searchTerm, 'gi');
      const actuaciones = contenido.actuaciones;
      const direcciones = contenido.direcciones;
      return regEx.test(contenido.descripcion) || regEx.test(contenido.titulo) || regEx.test(contenido.pais) ||
            actuaciones.some((actuacion) => { return regEx.test(actuacion.nombres) || regEx.test(actuacion.apellidos)}) ||
            direcciones.some((direccion) => { return regEx.test(direccion.nombres) || regEx.test(direccion.apellidos)});
    });
  }
}
