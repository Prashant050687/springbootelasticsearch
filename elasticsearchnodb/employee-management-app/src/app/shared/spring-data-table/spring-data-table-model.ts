import { Pageable } from '../models/pageable.model';
import { Type } from '@angular/core';

export class SpringDataPageableModel {
    totalElements: number;
    pageable: Pageable;
    content: any;
};

