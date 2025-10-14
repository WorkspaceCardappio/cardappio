import { Component } from '@angular/core';
import { TicketService } from '../service/ticket.service';

@Component({
  selector: 'app-tickets',
  imports: [],
  providers: [
    TicketService
  ],
  templateUrl: './ticket.component.html',
  styleUrl: './ticket.component.scss'
})
export class TicketsComponent{
  constructor(public service: TicketService){}
}
