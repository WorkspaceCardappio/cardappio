import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { EntityService } from '../entity-service/entity.service';
import { Ticket } from '../model/ticket';


@Injectable({
  providedIn: 'root'
})
export class TicketService extends EntityService<Ticket, string>{

  override resource: string = `${environment.apiUrl }/tickets`;

  constructor(public http: HttpClient){
    super(http);
  }

}
