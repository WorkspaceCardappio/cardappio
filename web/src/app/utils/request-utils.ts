import { TableLazyLoadEvent } from "primeng/table";
import { Filter } from "../model/filter.model";
import { SortItem } from "../model/order-item.model";
import { RequestParams } from "../model/request-params.model";


export class RequestUtils {

  static build(event: TableLazyLoadEvent): string {

    const page = (event.first ?? 0) / (event?.rows ?? 20);
    const size = event.rows ?? 20;
    const sortField = event.sortField ?? '';
    const sortOrder = event.sortOrder === 1 ? 'asc' : 'desc';

    let request = `page=${page}&size=${size}`;

    if (sortField) {
      request += `&sort=${sortField},${sortOrder}`;
    }

    return request;
  }

  static buildRequest(params: RequestParams): string {

    return [
      this.buildSearch(params.filters),
      this.buildSort(params.orders),
      this.buildPage(params.page),
      this.buildSize(params.size)
    ]
      .filter(value => value !== '')
      .join('&');
  }

  static buildSearch(filters: Filter[]): string {

    if (!filters.length)
      return '';

    const filter = filters
      .map(filter => `${filter.field}${filter.condition}${filter.value}`)
      .join(';');

    return `search=${filter}`;
  }

  static buildSort(sorts: SortItem[]): string {

    if (!sorts.length)
      return '';

    return sorts
      .map(order => `sort=${order.field},${order.order}`)
      .join('&');
  }

  static buildPage(page: number = 1): string {
    return `page=${page}`;
  }

  static buildSize(size: number = 20): string {
    return `size=${size}`;
  }

}
