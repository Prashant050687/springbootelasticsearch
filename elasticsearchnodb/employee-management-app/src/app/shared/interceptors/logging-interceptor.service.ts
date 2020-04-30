import { HttpInterceptor, HttpRequest, HttpHandler, HttpEventType, HttpErrorResponse } from '@angular/common/http';
import { tap, retry, catchError } from 'rxjs/operators';
import { Observable, throwError, Subject } from 'rxjs';
import { Injectable } from '@angular/core';
import { ErrorService } from '../../error/error.service';

@Injectable()
export class LoggingInterceptorService implements HttpInterceptor {

    constructor(private errorService: ErrorService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler) {
        /* console.log('Out going Request :');
        console.log('URL: ' + req.url);
        console.log('-------------Headers----------- ');
        console.log(req.headers);
        console.log('------------------------ '); */


        //generic error handling
        return next.handle(req)
            .pipe(
                tap(event => {
                    if (event.type === HttpEventType.Response) {
                        /*   console.log(' Incoming Response :');
                          console.log('-------------Response Body----------- ');
                          console.log(event.body);
                          console.log('------------------------ ');
                          console.log('Status: ' + event.status); */
                    }

                }),
                //generic error handling
                catchError((error: HttpErrorResponse) => {

                    //console.log('-------------Response Error----------- ');
                    console.log(error);
                    // console.log('------------------------ ');
                    //handle error such that this will be picked up by the error component and rethrow 
                    //so that the components subscribing to the observable can write custom code for error 
                    //handling like stopping progress bar, resetting component state etc.
                    this.errorService.brodcastError(error);
                    return throwError(error);
                })
            );
    }
}