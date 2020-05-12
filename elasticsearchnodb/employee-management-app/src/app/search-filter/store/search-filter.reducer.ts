import { SearchCriteria } from 'src/app/shared/models/search.criteria';
import * as searchFilterActions from './search-filter.actions';

export interface State {
    searchCriteria: SearchCriteria;
}

/**Define intial state for the reducer */
const initialState: State = {
    searchCriteria: null
};

//for first time, state will be initialized to initial state using the syntax below.
export function searchFilterReducer(state: State = initialState,
    action: searchFilterActions.typeOfSearchFilterActions) {

    switch (action.type) {
        case searchFilterActions.APPLY_SEARCH_FILTER:
            const searchCriteria = {
                ...state,
                searchCriteria: action.payload
            }
            console.log(searchCriteria)
            return searchCriteria;
            break;
        case searchFilterActions.CLEAR_SEARCH_FILTER:
            return {
                ...state,
                searchCriteria: null
            }
            break;
        default:
            return state;
            break;
    }

}
