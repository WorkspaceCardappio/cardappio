import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { EntityService } from "cardappio-component-hub";

@Injectable({
  providedIn: 'root'
})
export class OrderService extends EntityService<any, any> {

  constructor(http: HttpClient) {
    super(http);
    this.resource = 'http://localhost:8080/orders';
  }

}
