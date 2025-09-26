import { TableStatus } from './table-status.type';

export interface TableRestaurant {
  id: string,
  number: string,
  status: TableStatus,
  places: number
}
