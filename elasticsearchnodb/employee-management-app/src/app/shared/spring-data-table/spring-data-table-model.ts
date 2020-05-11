import { Pageable } from '../models/pageable.model';


export class SpringDataPageableModel {
    constructor(public totalElements: number, public pageable: Pageable, public content: any) { };
};

