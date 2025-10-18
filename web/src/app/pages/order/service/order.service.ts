import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { environment } from "../../../../environments/environment";
import { EntityService } from "../../../entity-service/entity.service";
import { Page } from "../../../model/page.model";

@Injectable({
  providedIn: 'root'
})
export class OrderService extends EntityService<any, any> {

  override resource: string = `http://localhost:8080/orders`;

  constructor(public http: HttpClient) {
    super(http);
  }

  findToTicket(id: string, params: string): Observable<Page<any>> {
    return this.http.get<Page<any>>(`${this.resource}/to-ticket/${id}?${params}`);
  }

}
