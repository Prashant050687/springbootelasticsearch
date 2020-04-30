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


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'employees', component: EmployeeComponent },
  { path: 'newemployee', component: EmployeeNewComponent },
  //resolver guarantees that the employee details will be loaded only after selectedEmployee is fetched.
  { path: 'employeedetails/:id', component: EmployeeDetailComponent, resolve: { selectedEmployee: EmployeeDetailResolver } },
  { path: "not-found", component: PageNotFoundComponent },
  { path: "**", redirectTo: '/not-found' },
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
