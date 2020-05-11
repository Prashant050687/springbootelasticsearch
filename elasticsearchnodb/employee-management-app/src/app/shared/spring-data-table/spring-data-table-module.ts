import { NgModule } from '@angular/core';
import { NgxPaginationModule } from 'ngx-pagination';
import { SpringDataTableComponent } from './spring-data-table.component';
import { CommonModule } from '@angular/common';
import { TrimpipePipe } from '../pipes/trimpipe.pipe';
import { FormsModule } from '@angular/forms';

@NgModule({
    declarations: [
        SpringDataTableComponent,
        TrimpipePipe
    ],
    imports: [
        NgxPaginationModule, CommonModule, FormsModule
    ],
    exports: [
        SpringDataTableComponent
    ]
})
export class SpringDataTableModule {

}