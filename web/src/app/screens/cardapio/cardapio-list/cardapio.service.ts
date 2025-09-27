import { Injectable } from '@angular/core';
import { EntityService } from "cardappio-component-hub";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CardapioService extends EntityService<any, any> {

  constructor(http: HttpClient) {
    super(http);
    this.resource = 'http://localhost:8080/menus';
  }
}
