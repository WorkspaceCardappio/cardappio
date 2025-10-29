import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { environment } from "../../../../../../environments/environment";
import { EntityService } from "../../../../../entity-service/entity.service";

@Injectable({
  providedIn: 'root'
})
export class OrderAdditionalService extends EntityService<any, any> {

  override resource: string = `${environment.apiUrl}/order-additionals`;

  constructor(public http: HttpClient) {
    super(http);
  }

  createItems(body: any[]) {
    return this.http.post(`${this.resource}/items`, body);
  }

}

