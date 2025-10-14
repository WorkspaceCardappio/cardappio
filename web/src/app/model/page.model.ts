export interface Page<V> {
  content: V[];
  empty: boolean,
  first: boolean,
  last: boolean,
  number: number,
  size: number,
  totalElements: number,
  totalPages: number,
  sort: any,
  pageable: any
}
