import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';

import { SearchCriteria } from '../shared/models/search.criteria';
import { SearchCondition } from '../shared/models/search.condition';
import { EmployeeService } from '../employee/service/employee.service';

import { Store } from '@ngrx/store';
import * as fromApp from '../store/app.reducer';
import * as SearchFilterActions from './store/search-filter.actions'
import { trigger, state, style, transition, animate } from '@angular/animations';


@Component({
  selector: 'app-search-filter',
  templateUrl: './search-filter.component.html',
  styleUrls: ['./search-filter.component.css'],
  animations: [

    trigger('list1', [
      state('in', style({
        opacity: 1,
        transform: 'translateX(0)'
      })),
      transition('void => *', [
        style({
          opacity: 0,
          transform: 'translateY(-100px)'
        }),
        animate(300)
      ]),
      transition('* => void', [
        animate(300, style({
          transform: 'translateX(100px)',
          opacity: 0
        }))
      ])
    ]),


  ]

})
export class SearchFilterComponent implements OnInit {

  searchCriteria: SearchCriteria;

  //This will be used to propogate the search/ clear button click to the employee component
  //so that the employee component can close the modal dialog
  @Output() closeDialog = new EventEmitter<boolean>();

  searchForm: FormGroup;
  constructor(private employeeService: EmployeeService, private store: Store<fromApp.AppState>) { }

  ngOnInit(): void {
    let searchCriterias = new FormArray([]);
    this.searchForm = new FormGroup({
      'searchCriterias': searchCriterias
    });



    this.store.select('searchFilter').subscribe(searchCriteriaState => {
      this.searchCriteria = searchCriteriaState.searchCriteria;
      if (this.searchCriteria) {
        for (let searchCondition of this.searchCriteria.getConditions()) {
          this.getSearchCriteriaArray().push(new FormGroup({
            'criteria': new FormControl(searchCondition['fieldName'], [Validators.required]),
            'operation': new FormControl(searchCondition['operation'], [Validators.required]),
            'searchCriteriaValue1': new FormControl(searchCondition['value1'], [Validators.required]),
            'searchCriteriaValue2': new FormControl(searchCondition['value2'])
          }));
        }
      }
    })

    /*
     if (this.searchCriteria) {
       for (let searchCondition of this.searchCriteria.getConditions()) {
         this.getSearchCriteriaArray().push(new FormGroup({
           'criteria': new FormControl(searchCondition['fieldName'], [Validators.required]),
           'operation': new FormControl(searchCondition['operation'], [Validators.required]),
           'searchCriteriaValue1': new FormControl(searchCondition['value1'], [Validators.required]),
           'searchCriteriaValue2': new FormControl(searchCondition['value2'])
         }));
       }
     }
     */
  }

  isFormValid(): boolean {

    if (this.getControls().length > 0) {
      const invalidComponents = this.getControls().filter((formGroup: FormGroup) => !formGroup.valid);
      if (invalidComponents.length <= 0) {
        return true;
      }
    }
    return false;
  }

  onSubmit() {
    let searchCriteriasFromForm = this.searchForm.value['searchCriterias'];

    let searchConditions = searchCriteriasFromForm.map((control: any) => {

      let searchCondition = new SearchCondition(control['criteria'], control['operation'],
        control['searchCriteriaValue1'], control['searchCriteriaValue2']);
      return searchCondition;

    });

    this.searchCriteria = new SearchCriteria(searchConditions);

    //This is used to pass search criteria to the employee list component
    //this.employeeService.searchCriteriaEmitter.next(this.searchCriteria);
    this.store.dispatch(new SearchFilterActions.ApplySearchFilter(this.searchCriteria))
    this.closeDialog.emit(true);

  }

  getSearchCriteriaArray() {
    return (<FormArray>this.searchForm.get('searchCriterias'));
  }

  getControls() { // a getter!
    return (<FormArray>this.searchForm.get('searchCriterias')).controls;
  }

  onAddCriteria() {
    this.getSearchCriteriaArray().push(new FormGroup({
      'criteria': new FormControl(null, [Validators.required]),
      'operation': new FormControl(null, [Validators.required]),
      'searchCriteriaValue1': new FormControl(null, [Validators.required]),
      'searchCriteriaValue2': new FormControl(null)
    }));
  }

  onClearCriteria() {
    const count = this.getControls().length;
    this.getControls().splice(0, count);
    this.searchForm.reset();
    //This is used to pass search criteria to the employee list component
    this.store.dispatch(new SearchFilterActions.ClearSearchFilter())
    //this.employeeService.searchCriteriaEmitter.next(null);
    this.closeDialog.emit(true);

  }

  onRemoveCriteria(index: number) {
    const count = this.getControls().length;
    //if only one criteria is present, then invoke clear criteria to also inform employee list component
    if (count === 1) {
      this.onClearCriteria();
    }
    (<FormArray>this.searchForm.get('searchCriterias')).removeAt(index);

  }

}
