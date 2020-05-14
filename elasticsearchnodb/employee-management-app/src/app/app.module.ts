import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EmployeeListComponent } from './employee/employee-list/employee-list.component';
import { EmployeeDetailComponent } from './employee/employee-detail/employee-detail.component';
import { EmployeeNewComponent } from './employee/employee-new/employee-new.component';
import { HeaderComponent } from './header/header.component';
import { SearchFilterComponent } from './search-filter/search-filter.component';
import { HomeComponent } from './home/home.component';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { EmployeeComponent } from './employee/employee.component';

import { NgxUiLoaderService } from 'ngx-ui-loader';

import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MasterDataLoaderService } from './shared/service/master-data-loader-service';

import { LoggingInterceptorService } from './shared/interceptors/logging-interceptor.service';
import { ErrorComponent } from './error/error.component';
import { AuthComponent } from './auth/auth.component';
import { SpringDataTableModule } from './shared/spring-data-table/spring-data-table-module';
import { StoreModule } from '@ngrx/store';
import * as fromApp from './store/app.reducer';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';



export function masterDataProviderFactory(provider: MasterDataLoaderService) {
  return () => provider.load();
}

@NgModule({
  declarations: [
    AppComponent,
    EmployeeListComponent,
    EmployeeDetailComponent,
    EmployeeNewComponent,
    HeaderComponent,
    SearchFilterComponent,
    HomeComponent,
    PageNotFoundComponent,
    EmployeeComponent,
    ErrorComponent,
    AuthComponent,



  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule, BrowserAnimationsModule, NgxUiLoaderModule, ReactiveFormsModule, NgbModule,
    SpringDataTableModule, StoreModule.forRoot(fromApp.appReducer)],
  providers: [NgxUiLoaderService, MasterDataLoaderService,
    { provide: APP_INITIALIZER, useFactory: masterDataProviderFactory, deps: [MasterDataLoaderService], multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: LoggingInterceptorService, multi: true }],
  bootstrap: [AppComponent],
})
export class AppModule { }
