import { BaseAuditDTO } from '../../shared/models/base.audit.model';
import { ContractTypeDTO } from './contract.type.model';
import { EmployeeType } from './employee.type.model';

export class EmployeeDTO extends BaseAuditDTO {
  id: string;
  firstName: string;
  lastName: string;
  salary: number;
  contractType: ContractTypeDTO;
  employeeType: EmployeeType;
  projectName: string
  reportingEmployees: number
  constructor(
    id: string,
    firstName: string,
    lastName: string,
    salary: number,
    contractType: ContractTypeDTO,
    employeeType: EmployeeType,
    projectName?: string,
    reportingEmployees?: number
  ) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
    this.contractType = contractType;
    this.employeeType = employeeType;
    this.projectName = projectName;
    this.reportingEmployees = reportingEmployees;

  }
}
