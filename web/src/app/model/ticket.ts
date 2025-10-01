import { Person } from "./person"
import { TableRestaurant } from "./table-restaurant"
import { TicketStatus } from "./ticket-status"

export interface Ticket{
    id: string,
    number: string, 
    status:TicketStatus,
    owner: Person,
    table: TableRestaurant,
}