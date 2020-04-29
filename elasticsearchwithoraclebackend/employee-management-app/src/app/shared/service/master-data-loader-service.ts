import { HttpClient } from '@angular/common/http';
import { ContractTypeDTO } from 'src/app/employee/models/contract.type.model';
import { Injectable } from '@angular/core';
import { resolve } from 'dns';

@Injectable()
export class MasterDataLoaderService {

    contractTypes: ContractTypeDTO[];

    constructor(private http: HttpClient) { }

    getContractTypes() {
        return this.contractTypes;
    }


    load() {
        const promise = new Promise((resolve, reject) => {
            //convert observable to promise
            //promise.then is used to process the data, and it takes two arguments, respose and error
            this.http.get<ContractTypeDTO>('assets/contract.type.data.json').toPromise().then((response: any) => {
                //Success
                this.contractTypes = response;
                resolve();
            }, (error: any) => {
                //error
                reject(error);
            })
        });

        return promise;


    }


}