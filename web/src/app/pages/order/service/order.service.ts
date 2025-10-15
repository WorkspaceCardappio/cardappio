import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment } from "../../../../environments/environment";
import { EntityService } from "../../../entity-service/entity.service";

@Injectable({
  providedIn: 'root'
})
export class OrderService extends EntityService<any, any> {

  override resource: string = `http://localhost:8080/orders`;

  constructor(public http: HttpClient) {
    super(http);
  }

}
