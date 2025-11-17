import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { environment } from "../../../../environments/environment";
import { EntityService } from "../../../entity-service/entity.service";


@Injectable({
  providedIn: 'root'
})
export class TicketService extends EntityService<any, any>{

  override resource: string = `${environment.apiUrl}/tickets`;

  constructor(public http: HttpClient) {
    super(http);
  }

  split(id: string, orders: string[], ticketToSplit: any) {
    return this.http.post(`${this.resource}/split/${id}`, { orders: orders, ticket: ticketToSplit });
  }

  findTotalByIds(ids: string[]) {
    return this.http.post<any>(`${this.resource}/total-by-ids`, { ids: ids });
  }

}
