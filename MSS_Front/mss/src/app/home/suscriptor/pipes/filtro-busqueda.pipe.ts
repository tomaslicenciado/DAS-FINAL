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
      const cAct = contenido.actuaciones.some(actuacion => {return actuacion.apellidos.toLowerCase().includes(searchTerm) || 
                                              actuacion.nombres.toLowerCase().includes(searchTerm)});
      const cDir = contenido.direcciones.some(direccion => {return direccion.apellidos.toLowerCase().includes(searchTerm) || 
                                              direccion.nombres.toLowerCase().includes(searchTerm)});
      const cDesc = contenido.descripcion.toLowerCase().includes(searchTerm);
      const cTit = contenido.titulo.toLowerCase().includes(searchTerm);
      const cPais = contenido.pais.toLowerCase().includes(searchTerm);
      return cAct || cDir || cDesc || cTit || cPais;
    });
  }
}
