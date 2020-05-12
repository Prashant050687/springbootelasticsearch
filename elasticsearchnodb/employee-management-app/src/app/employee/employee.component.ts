import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Store } from '@ngrx/store';
import * as fromApp from '../store/app.reducer';


@Component({
    selector: 'app-employee',
    templateUrl: './employee.component.html',
    styleUrls: ['./employee.component.css'],
})
export class EmployeeComponent implements OnInit {

    searchTriggered = false;

    ngOnInit(): void {
        this.store.select('searchFilter').subscribe(searchCriteriaState => {
            if (searchCriteriaState.searchCriteria) {
                this.searchTriggered = true;
            } else {
                this.searchTriggered = false;
            }
        })
    }

    constructor(private modalService: NgbModal, private store: Store<fromApp.AppState>) { }

    open(content) {
        const modalRef = this.modalService.open(content, { size: 'lg', backdrop: 'static' });

    }

    onCloseDialog() {
        this.modalService.dismissAll('search or clear clicked');
    }
}