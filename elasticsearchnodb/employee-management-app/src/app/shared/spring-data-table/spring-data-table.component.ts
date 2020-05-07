import { Component, OnInit, Input, EventEmitter, Output, SimpleChanges, OnChanges } from '@angular/core';
import { SpringDataPageableModel } from './spring-data-table-model';

/**
 * Component to render tables which fetches paginated data using spring-data api.
 */
@Component({
  selector: 'spring-data-table',
  templateUrl: './spring-data-table.component.html',
  styleUrls: ['./spring-data-table.component.css']
})
export class SpringDataTableComponent implements OnInit, OnChanges {

  paginationConfig: any;
  error: Error = null;

  /** Mandatory Attribute: Provides the heading columns for the table. */
  @Input() tableHeaders: Array<string>;
  /** Mandatory Attribute: This will be used as key to fetch the data from the object using the specified key */
  @Input() dataKeys: Array<string>;
  /** Mandatory Attribute: Actual Data which should extend the type SpringDataPageableModel  */
  @Input() data: SpringDataPageableModel;
  /** Page size used for the table. Defaulted to 4. The number provided should be present in the pagesizeOptions  */
  @Input() pageSize: number = 4;
  /** Options for page sizee. Defaulted to [2, 4, 10]. */
  @Input() pageSizeOptions: Array<number> = [2, 4, 10];


  /** Provides the handle for table's row select event and provides the row object as event data */
  @Output('rowSelect') rowSelectEmitter = new EventEmitter<any>();
  /** Provides the handle for table's pagesize change event and provides updated page size */
  @Output('pageSizeChange') pageSizeChangedEmitter = new EventEmitter<any>();
  /** Provides the handle for table's page change change event and provides updated page number */
  @Output('pageChange') pageNoChangedEmitter = new EventEmitter<any>();


  constructor() { }

  ngOnInit(): void {
    this.checkRequiredFields(this.tableHeaders, 'tableHeaders');
    this.checkRequiredFields(this.dataKeys, 'dataKeys');
    this.checkRequiredFields(this.pageSize, 'pageSize');

    if (!this.pageSizeOptions.includes(this.pageSize)) {
      this.error = new Error(" Select Page Size attribute ssuch that it belongs to pageSizeOptions attribute");
      throw this.error;
    }

  }

  ngOnChanges(changes: SimpleChanges): void {
    //Called before any other lifecycle hook. Use it to inject dependencies, but avoid any serious work here.
    //Add '${implements OnChanges}' to the class.

    if (this.data) {

      this.paginationConfig = {
        itemsPerPage: this.data.pageable.pageSize,
        currentPage: (this.data.pageable.pageNumber + 1),
        totalItems: this.data.totalElements
      };
    }

  }

  getCellData(record: any, key: string) {
    if (key.includes('.')) {
      let splitkey1 = key.substring(0, key.lastIndexOf('.'));
      let splitkey2 = key.substring(key.lastIndexOf('.') + 1, key.length);

      return this.getCellData(record[splitkey1], splitkey2);
    }
    return record[key];
  }

  onPaginationSizeChangeSelect(pageSize: number) {

    this.pageSizeChangedEmitter.emit(pageSize);
  }

  pageChanged(pageNo: number) {

    this.pageNoChangedEmitter.emit(pageNo);

  }

  onRowSelect(record: any) {
    this.rowSelectEmitter.emit(record);
  }

  checkRequiredFields(input: any, type: string) {

    if (!input || input.length <= 0) {
      this.error = new Error("Attribute " + type + " is required");
      throw this.error;
    }
  }

}
