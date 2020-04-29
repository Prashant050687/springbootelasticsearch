
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { EmployeeDTO } from '../models/employee.model';
import { Observable } from 'rxjs/internal/Observable';
import { Injectable } from '@angular/core';
import { EmployeeService } from '../service/employee.service';

@Injectable({
    providedIn: "root"
})
export class EmployeeDetailResolver implements Resolve<EmployeeDTO> {

    constructor(private employeeService: EmployeeService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): EmployeeDTO | Observable<EmployeeDTO> | Promise<EmployeeDTO> {
        if (route.params['id']) {
            return this.employeeService.getEmployeeById(Number(route.params['id']));
        }
        return null;

    }

}