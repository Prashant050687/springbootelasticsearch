import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorDetail } from './error.detail.model';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  errorSubject = new Subject<ErrorDetail>();

  brodcastError(errorResponse: HttpErrorResponse) {
    let errorDetail: ErrorDetail;
    if (errorResponse.error && errorResponse.error instanceof ErrorDetail) {
      errorDetail = errorResponse.error;

    } else {
      errorDetail = new ErrorDetail();
      errorDetail.title = 'An Unknown Error has occured!';
      errorDetail.detail = errorResponse.message;
    }
    this.errorSubject.next(errorDetail);
  }

  constructor() { }
}
