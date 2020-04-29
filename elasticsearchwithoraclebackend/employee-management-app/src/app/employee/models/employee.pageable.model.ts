import { Sort } from '../../shared/models/sort.model';
import { EmployeeDTO } from './employee.model';

export class EmployeePageable {
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number;
    first: boolean;
    numberOfElements: number;
    sort: Sort;
    content: EmployeeDTO[];
}