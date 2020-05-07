import { Component, OnInit } from '@angular/core';
import { MasterDataLoaderService } from './shared/service/master-data-loader-service';
import { ContractTypeDTO } from './employee/models/contract.type.model';
import { AuthService } from './auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'employee-management-app';
  contractTypes: ContractTypeDTO[];

  constructor(private masterdataService: MasterDataLoaderService, private authService: AuthService) {

  }

  ngOnInit() {
    this.contractTypes = this.masterdataService.getContractTypes();
    this.authService.autoLogin();

  }
}
