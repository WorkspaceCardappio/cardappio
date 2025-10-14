import { Person } from "../../../model/person"
import { TableRestaurant } from "../../../model/table-restaurant"
import { TicketStatus } from "./ticket-status"

export interface Ticket {
    id: string,
    number: string,
    status:TicketStatus,
    owner: Person,
    table: TableRestaurant,
}
