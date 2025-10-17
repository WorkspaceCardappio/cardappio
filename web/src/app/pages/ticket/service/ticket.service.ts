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

}
