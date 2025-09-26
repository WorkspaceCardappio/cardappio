import { Component, OnInit } from '@angular/core';
import { TicketService } from './service/ticket.service';
import { Ticket } from '../model/ticket';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ticket',
  imports: [
    CommonModule
  ],
  providers: [
    TicketService
  ],
  templateUrl: './ticket.component.html',
  styleUrl: './ticket.component.scss'
})
export class TicketComponent{
  //public ticketsList: Ticket[] = [];

  constructor(
    public service: TicketService,
    private router: Router
  ) {}

 /*ngOnInit(): void {
    this.service.findAll().subscribe({
      next: (data: Ticket[]) => {
        this.ticketsList = data;
        console.log('Tickets carregados: ', this.ticketsList)
      },
      error: (err) => {
        console.error('Erro ao buscar tickets:', err);
      }
    });
  }*/

  visualizarTicket(ticketId: string): void{
    console.log('Visualizar pedido ID:', ticketId);
    this.router.navigate(['/ticket-details', ticketId])
  }
}