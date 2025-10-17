import { Component } from '@angular/core';
import { TicketService } from '../service/ticket.service';

@Component({
  selector: 'app-ticket',
  imports: [],
  providers: [TicketService],
  templateUrl: './ticket-list.component.html',
  styleUrl: './ticket-list.component.scss',
})
export class TicketListComponent {
  constructor(public service: TicketService) {}
}
