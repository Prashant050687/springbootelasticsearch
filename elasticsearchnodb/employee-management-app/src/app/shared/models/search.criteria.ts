import { SearchCondition } from './search.condition';

export class SearchCriteria {


    constructor(private conditions: SearchCondition[]) {

    }

    getConditions() {
        return this.conditions;
    }
}