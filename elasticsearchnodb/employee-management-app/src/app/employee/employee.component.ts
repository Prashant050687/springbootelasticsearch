import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SearchCriteria } from '../shared/models/search.criteria';
import { SearchCondition } from '../shared/models/search.condition';
import { EmployeeService } from './service/employee.service';

@Component({
    selector: 'app-employee',
    templateUrl: './employee.component.html',
    styleUrls: ['./employee.component.css'],
})
export class EmployeeComponent implements OnInit {
    searchCriteria: SearchCriteria;
    ngOnInit(): void {
        this.employeeService.searchCriteriaEmitter.subscribe((searchCriteria: SearchCriteria) => {
            this.searchCriteria = searchCriteria;
        })
    }

    constructor(private modalService: NgbModal, private employeeService: EmployeeService) { }

    open(content) {
        const modalRef = this.modalService.open(content, { size: 'lg', backdrop: 'static' });

    }

    onCloseDialog() {
        this.modalService.dismissAll('search or clear clicked');
    }
}