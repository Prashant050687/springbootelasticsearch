import { ActionReducerMap } from '@ngrx/store';
import * as fromSearchFilter from '../search-filter/store/search-filter.reducer';

export interface AppState {
    searchFilter: fromSearchFilter.State
}

export const appReducer: ActionReducerMap<AppState> = {
    searchFilter: fromSearchFilter.searchFilterReducer
}