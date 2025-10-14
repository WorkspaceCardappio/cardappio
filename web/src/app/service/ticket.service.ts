import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub';
import { Ticket } from '../model/ticket';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class TicketService extends EntityService<Ticket, string>{

  override resource: string = `${environment.apiUrl}/tickets`;

  constructor(public http: HttpClient){
    super(http);
  }

}
