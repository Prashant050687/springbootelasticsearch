import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'trimpipe'
})
export class TrimpipePipe implements PipeTransform {

  transform(value: any): any {
    if (value.length === 0) {
      return value;
    }
    return value.trim();
  }

}
