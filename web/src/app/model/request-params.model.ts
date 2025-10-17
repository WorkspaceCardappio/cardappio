import { Filter } from './filter.model';
import { SortItem } from "./order-item.model";

export interface RequestParams {
  filters: Filter[];
  orders: SortItem[];
  page: number;
  size: number;
}
