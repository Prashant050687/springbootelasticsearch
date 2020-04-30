import { Component, OnInit } from '@angular/core';
import { EmployeeDTO } from '../models/employee.model';
import { EmployeeService } from '../service/employee.service';
import { ActivatedRoute, Params, Data, Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MasterDataLoaderService } from 'src/app/shared/service/master-data-loader-service';
import { ContractTypeDTO } from '../models/contract.type.model';

@Component({
  selector: 'app-employee-detail',
  templateUrl: './employee-detail.component.html',
  styleUrls: ['./employee-detail.component.css'],
})
export class EmployeeDetailComponent implements OnInit {
  selectedEmployee: EmployeeDTO;

  contractTypes: ContractTypeDTO[];
  editMode = false;

  employeeForm: FormGroup;

  saveSuccess = false;


  constructor(private employeeService: EmployeeService, private route: ActivatedRoute,
    private masterDataService: MasterDataLoaderService, private router: Router) { }

  ngOnInit(): void {

    this.contractTypes = this.masterDataService.getContractTypes();

    this.employeeForm = new FormGroup({

      'firstName': new FormControl(null, [Validators.required]),
      'lastName': new FormControl(null, [Validators.required]),
      'salary': new FormControl(null, [Validators.required, Validators.pattern(/^[1-9]+[0-9]*$/)]),
      'employeeType': new FormControl(null, [Validators.required]),
      'contractType': new FormControl(null, [Validators.required]),
      /*  'projectName': new FormControl(null),
       'reportingEmployees': new FormControl(null, [Validators.pattern(/^[1-9]+[0-9]*$/)]), */
    });


    //get data from resolver
    this.route.data.subscribe((data: Data) => {
      this.selectedEmployee = data['selectedEmployee'];
      //if employee data is present, then its an edit employee request. ie the component was called from employee list ->employee detail routing
      this.initFormForEdit();

    });

  }


  initFormForEdit() {
    if (this.selectedEmployee) {
      this.editMode = true;
      /* if (this.selectedEmployee.employeeType === 'PROJECT_LEADER') {
        this.projectLeader = true;
      } */
      this.employeeForm.patchValue({
        'firstName': this.selectedEmployee.firstName,
        'lastName': this.selectedEmployee.lastName,
        'salary': this.selectedEmployee.salary,
        'employeeType': this.selectedEmployee.employeeType,
        'contractType': this.selectedEmployee.contractType.id,
        /*   'projectName': this.selectedEmployee.projectName,
          'reportingEmployees': this.selectedEmployee.reportingEmployees */
      })
    }
  }

  changeEmployeeType(e) {
    if (e.target.value === 'PROJECT_LEADER') {
      // this.projectLeader = true;
      //this.getFormControls('projectName').setValidators([Validators.required])
    } else {
      //this.projectLeader = false;
      //this.getFormControls('projectName').clearValidators();

    }

    //update form and control validity
    this.getFormControls('projectName').updateValueAndValidity();
    this.employeeForm.updateValueAndValidity()
  }

  onSubmit() {
    let contractTypeId = Number(this.employeeForm.value['contractType']);

    let employee: EmployeeDTO = Object.assign(this.employeeForm.value);
    employee.contractType = this.fetchContractType(contractTypeId);

    if (this.selectedEmployee) {
      employee.id = this.selectedEmployee.id;
      employee.version = this.selectedEmployee.version;
    }

    this.employeeService.saveEmployee(employee, employee.employeeType).subscribe((employeeDTO: EmployeeDTO) => {
      this.selectedEmployee = employeeDTO;
      this.initFormForEdit();
      this.saveSuccess = true;
    })
  }


  onDelete() {
    this.employeeService.deleteEmployee(this.selectedEmployee.id, this.selectedEmployee.employeeType);
    this.router.navigate(['/employees']);
  }

  getFormControls(controlName: string) {
    return this.employeeForm.controls[controlName];
  }

  fetchContractType(contractTypeId: number): ContractTypeDTO {
    return this.contractTypes.find((contractType: ContractTypeDTO) => contractType.id == contractTypeId);
  }

}
