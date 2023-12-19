import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MssApiService } from 'src/app/api/resolvers/mss-api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  invalid: boolean = false;
  submited: boolean = false;

  constructor(private _fb: FormBuilder, private _service: MssApiService, private _router: Router){
    this.form = this._fb.group({
      email: new FormControl('',[Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required])
    })
  }

  ngOnInit(): void {
    if (this._service.isLogged())
      this._router.navigate(['/home/suscriptor']);
  }

  validar(): void{
    if (this.submited){
      this.invalid = false;
      for (const control in this.form.controls){
        if (this.form.controls[control].errors)
          this.invalid = true;
      }
    }
  }

  login(): void{
    this.submited = true;
    this.validar();
    if (!this.invalid && this.form.valid){
      this.submited = false;
      const email = this.form.controls['email'].value;
      const password = this.form.controls['password'].value;
      this.form.controls['email'].setValue('');
      this.form.controls['password'].setValue('');
      this._service.login(email, password);
      this._router.navigate(['/home/suscriptor']);
    }
  }
}
