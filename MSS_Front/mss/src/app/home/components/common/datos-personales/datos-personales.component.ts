import { Component, NgZone, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Codigo } from 'src/app/api/models/codigo';
import { IPlataforma } from 'src/app/api/models/i-plataforma';
import { IUser } from 'src/app/api/models/i-user';
import { RespuestaBean, getCodigo } from 'src/app/api/models/respuesta-bean';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';
import { MssApiRestResourceService } from 'src/app/api/resources/mss-api-rest-resource.service';
import { LoaderService } from 'src/app/core/loader/service/loader.service';
import { MensajeService } from 'src/app/core/mensajes/service/mensaje.service';

@Component({
  selector: 'app-datos-personales',
  templateUrl: './datos-personales.component.html',
  styleUrls: ['./datos-personales.component.css']
})
export class DatosPersonalesComponent implements OnInit{
  user: IUser = {
    id_usuario: 0,
    id_nivel: 0,
    nombres: '',
    apellidos: '',
    token: '',
    nivel: '',
    validado: false
  };
  formulario: FormGroup;
  submited: boolean = false;
  invalid: boolean = false;
  samePasswords: boolean = true;

  ngOnInit(): void {
    this.user =  this._service.getUser();
    this.inicializarFormulario();
  }

  constructor(private _router: Router, private _service: MssApiService, private _rsService: MssApiRestResourceService,
              private _ngZone: NgZone, private _msgSrv: MensajeService, private _fb: FormBuilder, public activeModal: NgbActiveModal,
              private _loader: LoaderService){
    this.formulario = this._fb.group({
      nombres: ['', Validators.required],
      apellidos: ['', Validators.required],
      contrasenaActual: ['', Validators.required],
      nuevaContrasena: [''],
      repetirNuevaContrasena: [''],
    });
  }

            
  private inicializarFormulario(): void {
    this.formulario = this._fb.group({
      nombres: [this.user.nombres, Validators.required],
      apellidos: [this.user.apellidos, Validators.required],
      contrasenaActual: ['', Validators.required],
      nuevaContrasena: [''],
      repetirNuevaContrasena: [''],
    });
  }
            
  guardarCambios(): void {
    this.submited = true;
    this.validar();
    if (!this.invalid && this.formulario.valid){
      this._loader.start();
      let nuevaPass = this.formulario.controls['nuevaContrasena'].value;
      let password = nuevaPass != '' ? nuevaPass : this.formulario.controls['contrasenaActual'].value;
      let nombres = this.formulario.controls['nombres'].value;
      let apellidos = this.formulario.controls['apellidos'].value;
      this._rsService.modificarDatosPersonales({token_usuario: this.user.token, nombres: nombres, apellidos: apellidos, password: password}).subscribe({
        next: (respuesta: RespuestaBean) => {
          if (getCodigo(respuesta) == Codigo.OK){
            this.user.apellidos = apellidos;
            this.user.nombres = nombres;
            this.activeModal.close();
          }
          else
            this._ngZone.run(() => this._msgSrv.showMessage({title: respuesta.body!, text: respuesta.mensaje}), 0);
        },
        error: (error) => {
          this._ngZone.run(() => this._msgSrv.showMessage({title: "Error en modificación de datos personales", text: error}), 0);
        },
        complete: () => {
          this._loader.complete();
        }
      });
    }
  }

  validar(): void{
    if (this.submited){
      this.invalid= false;
      this.samePasswords = true;
      for (const control in this.formulario.controls){
        if (this.formulario.controls[control].errors)
          this.invalid = true;
      }
      if (!this.invalid){
        const pass1 = this.formulario.controls['nuevaContrasena'].value;
        const pass2 = this.formulario.controls['repetirNuevaContrasena'].value;
        if (pass1 != pass2 && (pass1 != "" || pass2 != "")){
          this.samePasswords = false;
          this.invalid = true;
        }
      }
    }
  }
}
