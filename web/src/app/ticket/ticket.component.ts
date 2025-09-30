import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { TicketService } from './service/ticket.service';
import { Ticket } from '../modal/ticket';
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

  constructor(public ticketService: TicketService){}
}