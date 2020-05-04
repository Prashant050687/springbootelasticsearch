import { EmployeeDTO } from '../models/employee.model';
import { ContractTypeDTO } from '../models/contract.type.model';

import { EventEmitter, Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { of } from 'rxjs';

import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { EmployeePageable } from '../models/employee.pageable.model';
import { Pageable } from '../../shared/models/pageable.model';
import { SearchCriteria } from 'src/app/shared/models/search.criteria';
import { EmployeeType } from '../models/employee.type.model';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  searchCriteriaEmitter = new Subject<SearchCriteria>();

  constructor(
    private http: HttpClient
  ) { }


  getAllEmployeesPageable(pageable: Pageable, searchCriteria: SearchCriteria): Observable<EmployeePageable> {

    if (searchCriteria) {
      console.log('Search done using criteria ');
    }
    let pageableParams = new HttpParams();
    pageableParams = pageableParams.append('page', String(pageable.pageNumber));
    pageableParams = pageableParams.append('size', String(pageable.pageSize));

    const url = environment.backendBaseUrl + 'employee/search';
    // let fileName = 'assets/employee.data.size' + pageable.pageSize + '.' + 'page' + pageable.pageNumber + '.json';
    return this.http.post<EmployeePageable>(url, searchCriteria, {
      params: pageableParams
    });


  }

  saveEmployee(employee: EmployeeDTO, employeeType: EmployeeType): Observable<EmployeeDTO> {
    const url = environment.backendBaseUrl + 'employee/';
    let employeeTypeParams = new HttpParams();
    employeeTypeParams = employeeTypeParams.append('employeeType', employeeType);
    return this.http.post<EmployeeDTO>(url, employee, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      params: employeeTypeParams
    })
  }

  deleteEmployee(id: string, employeeType: EmployeeType) {
    const url = environment.backendBaseUrl + 'employee/' + id;
    let employeeTypeParams = new HttpParams();
    employeeTypeParams = employeeTypeParams.append('employeeType', employeeType);
    return this.http.delete(url).subscribe(response => {
      //do nothing
    })
  }

  getEmployeeById(id: string): Observable<EmployeeDTO> {
    const url = environment.backendBaseUrl + 'employee/' + id;
    return this.http.get<EmployeeDTO>(url);
    //return of(this.employees.find((employee: EmployeeDTO) => employee.id == id));
  }


}
