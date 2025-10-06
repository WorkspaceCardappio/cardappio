import { Component } from '@angular/core';
import { TicketService } from '../service/ticket.service';
import { CardappioListComponent } from 'cardappio-component-hub';

@Component({
  selector: 'app-tickets',
  imports: [
    CardappioListComponent
  ],
  providers: [
    TicketService
  ],
  templateUrl: './ticket.component.html',
  styleUrl: './ticket.component.scss'
})
export class TicketsComponent{
  constructor(public service: TicketService){}
}