import { Sort } from '../../shared/models/sort.model';
import { EmployeeDTO } from './employee.model';
import { Pageable } from 'src/app/shared/models/pageable.model';
import { SpringDataPageableModel } from 'src/app/shared/spring-data-table/spring-data-table-model';


export class EmployeePageable extends SpringDataPageableModel {
    test: string;

}