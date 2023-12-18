import { Token } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Codigo } from 'src/app/api/models/codigo';
import { IBanner } from 'src/app/api/models/i-banner';
import { RespuestaBean, getCodigo } from 'src/app/api/models/respuesta-bean';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';

@Component({
  selector: 'app-abm-banners',
  templateUrl: './abm-banners.component.html',
  styleUrls: ['./abm-banners.component.css']
})
export class AbmBannersComponent implements OnInit{
  banners: IBanner[] = [];
  bannerForms: FormGroup[] = [];
  invalid: boolean = false;
  submited: boolean = false;

  constructor(private _fb: FormBuilder, private _service: MssApiService, private _route: ActivatedRoute,
            private _router: Router, private _mensajeService: MensajeService) { 
    
  }

  ngOnInit(): void {
    this._route.data.subscribe((data) => {
      const respuesta: RespuestaBean = data['banners'];
      if (getCodigo(respuesta) == Codigo.OK){
        this.banners = JSON.parse(respuesta.body!);
        this.banners.forEach((banner) => {
          this.bannerForms.push(this._fb.group({
            banner_code: [banner.banner_code, Validators.required],
            tarifa_base: [banner.tarifa_base, Validators.required],
            id_ult_publicidad: [banner.id_ult_publicidad, Validators.required],
            tarifa_uso_exclusivo: [banner.tarifa_uso_exclusivo, Validators.required]
          }))
        })
      } else {
        this._mensajeService.showMessage({title: respuesta.body, text: respuesta.mensaje});
      }
    })
  }

  validar(form: FormGroup): void{
    if (this.submited){
      this.invalid = false;
      for (const control in form.controls){
        if (form.controls[control].errors)
          this.invalid = true;
      }
    }
  }

  guardarCambios(form: FormGroup): void {
    this.submited = true;
    this.validar(form);
    if (!this.invalid && form.valid){
      const token = this._service.getUser().token;
      const banner_code = form.controls['banner_code'].value;
      const tarifa_base = form.controls['tarifa_base'].value;
      const tarifa_uso_exclusivo = form.controls['tarifa_uso_exclusivo'].value;
      console.log(banner_code, tarifa_base, tarifa_uso_exclusivo)
      this._service.modificarBanner(token, banner_code, tarifa_base, tarifa_uso_exclusivo);
    }
    this.invalid = false;
    this.submited = false;
    window.location.reload();
  }

  volver(){
    this._router.navigate(['/administrador']);
  }
}
