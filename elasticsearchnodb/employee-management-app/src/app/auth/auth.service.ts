import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { throwError, Observable, Subject, BehaviorSubject } from 'rxjs';
import { User } from './user.model';
import { Router } from '@angular/router';

export interface AuthResponseData {
  kind: string;
  idToken: string;
  email: string;
  refreshToken: string;
  expiresIn: string;
  localId: string;
  registered?: boolean;
}


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiKey: string = 'AIzaSyBDb9ZfGRlW468JEluu7l7vd4yZ9qPC70M';

  token: string = null;

  private tokenExpirationTimer: any;

  // user = new Subject<User>();
  //USe behaviour subject when you want to access the subject value at any point of time and not just reactively ie 
  //as soon as the subject is emitted. This can now be used in the datastorage service this way.
  user = new BehaviorSubject<User>(null);



  constructor(private http: HttpClient, private router: Router) { }

  registerUser(email: string, password: string): Observable<AuthResponseData> {
    let typeOfAuth = 'Registration';
    return this.http.
      post<AuthResponseData>('https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=' + this.apiKey,
        {
          email: email,
          password: password,
          returnSecureToken: true
        }
      )
      .pipe(catchError((err) => this.handleError(err, typeOfAuth)),
        tap((responseData: AuthResponseData) => {
          this.processAuthentication(responseData)
        }));
  }

  signIn(email: string, password: string): Observable<AuthResponseData> {
    let typeOfAuth = 'Signing In';
    return this.http.
      post<AuthResponseData>('https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=' + this.apiKey,
        {
          email: email,
          password: password,
          returnSecureToken: true
        }
      )
      .pipe(catchError((err) => this.handleError(err, typeOfAuth)),
        tap((responseData: AuthResponseData) => {
          this.processAuthentication(responseData)
        }));

  }

  /**
   * This method is used to handle cases when user clicks on refresh or types the url
   * This is referened in the app component and will check if the user data is present in local storage.
   */
  autoLogin() {
    const userData: {
      email: string,
      id: string,
      _token: string,
      _tokenExpirationDate: string
    } = JSON.parse(localStorage.getItem('userData'));
    if (!userData) {
      return;
    }
    const loadedUser = new User(userData.email, userData.id, userData._token, new Date(userData._tokenExpirationDate));

    if (loadedUser.token) {
      this.user.next(loadedUser);
      const expirationDuration = new Date(userData._tokenExpirationDate).getTime() - new Date().getTime();
      this.autoLogout(expirationDuration);
    }
  }

  logout() {
    this.user.next(null);
    this.router.navigate(['/auth']);
    localStorage.removeItem('userData');
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
  }

  /**
   * The method will be referenced from the login and auto login
   * @param expirationDuration - Expiration duration in milliseconds
   */
  autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout;
    }, expirationDuration);
  }

  private processAuthentication(authResponse: AuthResponseData) {
    //from firebase we get expires-in in the form of a string specifying after how many seconds after which the token expires

    //calculate expiration date
    const expiresInMillisecs = Number(authResponse.expiresIn) * 1000;
    const expirationDate = new Date(new Date().getTime() + expiresInMillisecs);
    const user = new User(authResponse.email, authResponse.localId, authResponse.idToken, expirationDate);
    this.user.next(user);
    //register auto logout.
    this.autoLogout(Number(expiresInMillisecs) * 1000);
    localStorage.setItem('userData', JSON.stringify(user));
  }

  private handleError(errprResponse: HttpErrorResponse, typeOfAuth: string) {
    let errorMessage = 'Unknown error occured';
    if (!errprResponse.error || !errprResponse.error.error) {
      return throwError(errorMessage);
    }

    switch (errprResponse.error.error.message) {
      case 'EMAIL_EXISTS':
        errorMessage = 'This email already exists';
        break;
      case 'EMAIL_NOT_FOUND':
        errorMessage = 'This email does not exist';
        break;
      case 'INVALID_PASSWORD':
        errorMessage = 'The password is incorrect';
        break;
      default:
        errorMessage = 'Unknown error occured during ' + typeOfAuth;
        break;
    }

    return throwError(errorMessage);
  }
}
