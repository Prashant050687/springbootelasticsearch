import { Sort } from './sort.model';

export interface Pageable {
    sort?: Sort;
    paged?: boolean;
    pageNumber: number;
    unpaged?: boolean;
    pageSize: number;
}
