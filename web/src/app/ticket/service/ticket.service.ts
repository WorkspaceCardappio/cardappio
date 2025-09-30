import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub';
import { Ticket } from '../../modal/ticket';


@Injectable()
export class TicketService extends EntityService<Ticket, string>{

  override resource: string = 'http://localhost:8080/tickets';

  constructor(public http: HttpClient){
    super(http);
  }
  
}