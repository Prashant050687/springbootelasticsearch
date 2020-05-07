import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService, AuthResponseData } from './auth.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  isLoginMode = true;

  error: string = null;

  constructor(private authService: AuthService, private router: Router, private ngxLoader: NgxUiLoaderService, ) { }

  ngOnInit(): void {

  }

  onSwitchMode() {
    this.isLoginMode = !this.isLoginMode;
  }

  onSubmit(form: NgForm) {
    if (!form.valid) {
      return
    }

    this.ngxLoader.start();


    if (!this.isLoginMode) {
      this.processRegisterUser(form);

    } else {
      this.processSignIn(form);
    }

    form.reset();
  }

  processSignIn(form: NgForm) {
    let authResponseObservable = this.authService.signIn(form.value.email, form.value.password);
    this.processAuthResponse(authResponseObservable);
  }


  processRegisterUser(form: NgForm) {
    let authResponseObservable = this.authService.registerUser(form.value.email, form.value.password);
    this.processAuthResponse(authResponseObservable);
  }

  processAuthResponse(authResponse: Observable<AuthResponseData>) {
    authResponse.subscribe(response => {
      this.ngxLoader.stop();
      console.log(response);
      this.router.navigate(['/employees']);
    },
      errorMessage => {
        this.error = errorMessage;
        this.ngxLoader.stop();
      });
  }


}
