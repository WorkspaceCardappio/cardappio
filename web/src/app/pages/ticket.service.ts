import { Injectable } from '@angular/core';
import { EntityService } from "cardappio-component-hub";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class TicketService extends EntityService<any, any>{

  override resource: string = `${environment.apiUrl}/tickets`;

  constructor(public http: HttpClient) {
    super(http);
  }

}
