import { TableStatus } from "./table-status";

export interface TableRestaurant {
    id:string,
    number:string,
    status:TableStatus,
    places:number
}
