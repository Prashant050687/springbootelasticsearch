import { Component, OnInit, OnDestroy } from '@angular/core';
import { ErrorService } from './error.service';
import { Subscription } from 'rxjs';

import { ErrorDetail } from './error.detail.model';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit, OnDestroy {

  errorDetail: ErrorDetail;
  subscription: Subscription;

  constructor(private errorService: ErrorService) { }

  ngOnInit() {
    this.subscription = this.errorService.errorSubject.subscribe((error: ErrorDetail) => {
      this.errorDetail = error;
    })
  }

  onHandleError() {
    this.errorDetail = null;
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}
