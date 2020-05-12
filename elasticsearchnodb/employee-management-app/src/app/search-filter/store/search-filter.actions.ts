import { Action } from '@ngrx/store';
import { SearchCriteria } from 'src/app/shared/models/search.criteria';



export const APPLY_SEARCH_FILTER = '[Search Filter] APPLY_SEARCH_FILTER';

export const CLEAR_SEARCH_FILTER = '[Search Filter] CLEAR_SEARCH_FILTER';

export class ApplySearchFilter implements Action {
    readonly type = APPLY_SEARCH_FILTER;

    constructor(public payload: SearchCriteria) { }
}

export class ClearSearchFilter implements Action {
    readonly type = CLEAR_SEARCH_FILTER;

}

export type typeOfSearchFilterActions =
    ApplySearchFilter
    | ClearSearchFilter
    ;
