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
import { NgxPaginationModule } from 'ngx-pagination';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { EmployeeComponent } from './employee/employee.component';

import { NgxUiLoaderService } from 'ngx-ui-loader';

import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { EmployeeDetailResolver } from './employee/employee-detail/employee-detail-resolver.service';
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MasterDataLoaderService } from './shared/service/master-data-loader-service';
import { TrimpipePipe } from './shared/pipes/trimpipe.pipe';
import { LoggingInterceptorService } from './shared/interceptors/logging-interceptor.service';
import { ErrorComponent } from './error/error.component';
import { AuthComponent } from './auth/auth.component';
import { SpringDataTableComponent } from './shared/spring-data-table/spring-data-table.component';




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
    TrimpipePipe,
    ErrorComponent,
    AuthComponent,
    SpringDataTableComponent
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, NgxPaginationModule,
    FormsModule, NgxUiLoaderModule, ReactiveFormsModule, NgbModule],
  providers: [NgxUiLoaderService, MasterDataLoaderService,
    { provide: APP_INITIALIZER, useFactory: masterDataProviderFactory, deps: [MasterDataLoaderService], multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: LoggingInterceptorService, multi: true }],
  bootstrap: [AppComponent],
})
export class AppModule { }
