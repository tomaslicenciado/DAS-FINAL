import { Component, Input } from '@angular/core';
import { IPublicacion } from 'src/app/api/models/i-publicacion';

@Component({
  selector: 'app-ad-banner',
  templateUrl: './ad-banner.component.html',
  styleUrls: ['./ad-banner.component.css']
})
export class AdBannerComponent {
  @Input() publicacion: IPublicacion = {
    banner_code: 0,
    url_contenido: "",
    url_imagen: "",
    codigo_unico_id: 0
  };

  getBannerStyle(): any {
    // Lógica para determinar el estilo del banner según el banner_code
    let style: any = {};

    if (this.publicacion.banner_code.toString().startsWith('1')) {
      style = {
        'width': '14%',
        'height': '90%',
        'position': 'fixed',
        'top': '9%',
        'left': '9px',
        'z-index': '1000'
      };
    } else if (this.publicacion.banner_code.toString().startsWith('2')) {
      style = {
        'width': '14%',
        'height': '90%',
        'position': 'fixed',
        'top': '9%',
        'right': '9px',
        'z-index': '1000'
      };
    } else if (this.publicacion.banner_code.toString().startsWith('3')) {
      style = {
        'width': '70%',
        'height': '14%',
        'position': 'fixed',
        'bottom': '9px',
        'left': '15%',
        'z-index': '1000'
      };
    }

    return style;
  }
}
