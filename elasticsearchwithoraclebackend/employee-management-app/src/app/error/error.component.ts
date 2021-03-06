import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorService } from './error.service';
import { Subscription } from 'rxjs';
import { map } from 'rxjs/operators';
import { ErrorDetail } from './error.detail.model';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit, OnDestroy {
  error: HttpErrorResponse;
  errorDetail: ErrorDetail;
  subscription: Subscription;

  constructor(private errorService: ErrorService) { }

  ngOnInit() {
    this.subscription = this.errorService.errorSubject.subscribe((error: HttpErrorResponse) => {
      this.error = error;
      this.errorDetail = error.error;

    })
  }

  onHandleError() {
    this.error = null;
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}
