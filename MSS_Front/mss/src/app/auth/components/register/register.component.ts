import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{
  form: FormGroup;
  invalid: boolean = false;
  submited: boolean = false;
  samePasswords: boolean = false;
  sameEmail: boolean = false;

  constructor(private _fb: FormBuilder, private _service: MssApiService, private _router: Router){
    this.form = this._fb.group({
      email1: new FormControl('',[Validators.required, Validators.email]),
      email2: new FormControl('',[Validators.required, Validators.email]),
      pass1: new FormControl('',[Validators.required]),
      pass2: new FormControl('',[Validators.required]),
      nombres: new FormControl('',[Validators.required]),
      apellidos: new FormControl('',[Validators.required])
    })
  }

  ngOnInit(): void {
    if (this._service.isLogged())
      this._router.navigate(['/home/suscriptor']);
  }

  validar(): void{
    if (this.submited){
      this.invalid= false;
      this.samePasswords = true;
      this.sameEmail = true;
      for (const control in this.form.controls){
        if (this.form.controls[control].errors)
          this.invalid = true;
      }
      if (!this.invalid){
        const email1 = this.form.controls['email1'].value;
        const email2 = this.form.controls['email2'].value;
        if (email1 != email2 && (email1 != "" || email2 != "")){
          this.sameEmail = false;
          this.invalid = true;
        }
      }
      if (!this.invalid){
        const pass1 = this.form.controls['pass1'].value;
        const pass2 = this.form.controls['pass2'].value;
        if (pass1 != pass2 && (pass1 != "" || pass2 != "")){
          this.samePasswords = false;
          this.invalid = true;
        }
      }
    }
  }

  register(): void{
    this.submited = true;
    this.validar();
    if (!this.invalid && this.form.valid){
      this.submited = false;
      let email = this.form.controls['email1'].value;
      let password = this.form.controls['pass1'].value;
      let nombres = this.form.controls['nombres'].value;
      let apellidos = this.form.controls['apellidos'].value;
      this._service.register(email, password, nombres, apellidos, []);
      this._router.navigate(['/home/suscriptor']);
    }
  }
}
