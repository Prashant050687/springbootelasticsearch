import { HttpClient } from '@angular/common/http';
import { ContractTypeDTO } from 'src/app/employee/models/contract.type.model';
import { Injectable } from '@angular/core';
import { resolve } from 'dns';
import { ErrorService } from 'src/app/error/error.service';

@Injectable()
export class MasterDataLoaderService {

    contractTypes: ContractTypeDTO[];

    constructor(private http: HttpClient, private errorService: ErrorService) { }

    getContractTypes() {
        return this.contractTypes;
    }


    load() {
        const promise = new Promise((resolve, reject) => {
            const url = 'http://localhost:7001/employee/contracTypes';
            //const url = 'assets/contract.type.data.json';

            //convert observable to promise
            //promise.then is used to process the data, and it takes two arguments, respose and error
            this.http.get<ContractTypeDTO[]>(url).toPromise().then((response: any) => {
                //Success
                this.contractTypes = response;
                resolve();
            }, (error: any) => {
                //error
                this.errorService.brodcastError(error);
                reject(error);
            })
        });

        return promise;


    }


}