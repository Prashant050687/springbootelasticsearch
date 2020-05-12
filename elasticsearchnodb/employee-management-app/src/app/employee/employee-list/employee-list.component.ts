import { Component, OnInit, OnDestroy } from '@angular/core';
import { EmployeeService } from '../service/employee.service';
import { EmployeeDTO } from '../models/employee.model';

import { Router } from '@angular/router';
import { EmployeePageable } from '../models/employee.pageable.model';
import { NgxUiLoaderService } from 'ngx-ui-loader';


import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { SearchCriteria } from 'src/app/shared/models/search.criteria';
import { Subscription } from 'rxjs';

import { Store } from '@ngrx/store';
import * as fromApp from '../../store/app.reducer';
import * as SearchFilterActions from '../../search-filter/store/search-filter.actions'

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css'],
})
export class EmployeeListComponent implements OnInit, OnDestroy {


  pageSize: number = 4;

  searchTriggered = false;

  subscription: Subscription;
  searchCriteria: SearchCriteria;

  employeesPageable: EmployeePageable;


  constructor(private employeeService: EmployeeService, private router: Router,
    private ngxLoader: NgxUiLoaderService, private modalService: NgbModal,
    private store: Store<fromApp.AppState>
  ) { }

  ngOnInit(): void {
    this.ngxLoader.start();
    /*  this.subscription = this.employeeService.searchCriteriaEmitter.subscribe((searchCriteria: SearchCriteria) => {
       this.searchCriteria = searchCriteria;
       this.searchTriggered = true;
       this.getEmployeeBasedonPageNumber(0);
 
     }) */

    this.subscription = this.store.select('searchFilter').subscribe(searchCriteriaState => {
      this.searchCriteria = searchCriteriaState.searchCriteria;
      this.searchTriggered = true;
      this.getEmployeeBasedonPageNumber(0);
    })
    //Reload from first page
    if (!this.searchTriggered) {
      console.log('inside search not triggered')
      this.getEmployeeBasedonPageNumber(0);
    }

  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }


  onPaginationSizeChange(pageSize: number) {
    this.pageSize = pageSize;

    //Reload from first page
    this.getEmployeeBasedonPageNumber(0);
  }


  pageChanged(pageNo: number) {

    this.getEmployeeBasedonPageNumber((pageNo - 1));
  }

  getEmployeeBasedonPageNumber(page: number): void {

    this.employeeService.getAllEmployeesPageable({ pageNumber: page, pageSize: this.pageSize }, this.searchCriteria)
      .subscribe((data: EmployeePageable) => {
        this.employeesPageable = data;
        this.ngxLoader.stop();
      }, error => {
        this.ngxLoader.stop();
      });

  }

  onEmployeeSelect(employee: EmployeeDTO) {
    let selectedEmployee = this.employeesPageable.content.find((employee: EmployeeDTO) => employee.id == employee.id);
    this.router.navigate(['/employeedetails/' + employee.id]);
  }


}
