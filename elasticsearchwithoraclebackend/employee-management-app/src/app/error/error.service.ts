import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  errorSubject = new Subject<HttpErrorResponse>();

  brodcastError(errorResponse: HttpErrorResponse) {
    this.errorSubject.next(errorResponse);
  }

  constructor() { }
}
