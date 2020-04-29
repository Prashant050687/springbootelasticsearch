import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { EmployeeDTO } from '../employee/models/employee.model';
import { SearchCriteria } from '../shared/models/search.criteria';
import { SearchCondition } from '../shared/models/search.condition';
import { EmployeeService } from '../employee/service/employee.service';

@Component({
  selector: 'app-search-filter',
  templateUrl: './search-filter.component.html',
  styleUrls: ['./search-filter.component.css']
})
export class SearchFilterComponent implements OnInit {

  @Input('inputData') searchCriteria: SearchCriteria;
  //This will be used to propogate the search/ clear button click to the employee component
  //so that the employee component can close the modal dialog
  @Output() closeDialog = new EventEmitter<boolean>();

  searchForm: FormGroup;
  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {

    let searchCriterias = new FormArray([]);

    this.searchForm = new FormGroup({

      'searchCriterias': searchCriterias

    });

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
    this.employeeService.searchCriteriaEmitter.next(this.searchCriteria);
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
    this.employeeService.searchCriteriaEmitter.next(null);
    this.closeDialog.emit(true);

  }

  onRemoveCriteria(index: number) {
    const count = this.getControls().length;
    //if only one criteria is present, then invoke clear criteria to also inform employee list component
    if (count === 1) {
      this.onClearCriteria();
    }
    this.getControls().splice(index, 1);
  }

}
