import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { EmployeeListComponent } from './employee/employee-list/employee-list.component';
import { EmployeeNewComponent } from './employee/employee-new/employee-new.component';
import { EmployeeDetailComponent } from './employee/employee-detail/employee-detail.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { EmployeeDetailResolver } from './employee/employee-detail/employee-detail-resolver.service';
import { SearchFilterComponent } from './search-filter/search-filter.component';
import { EmployeeComponent } from './employee/employee.component';
import { AuthComponent } from './auth/auth.component';
import { AuthGuard } from './auth/auth.guard';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'auth', component: AuthComponent },
  { path: 'employees', component: EmployeeComponent, canActivate: [AuthGuard] },
  { path: 'newemployee', component: EmployeeNewComponent, canActivate: [AuthGuard] },
  //resolver guarantees that the employee details will be loaded only after selectedEmployee is fetched.
  {
    path: 'employeedetails/:id', component: EmployeeDetailComponent,
    canActivate: [AuthGuard],
    resolve: { selectedEmployee: EmployeeDetailResolver }
  },
  { path: "not-found", component: PageNotFoundComponent },
  { path: "**", redirectTo: '/not-found' },
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
