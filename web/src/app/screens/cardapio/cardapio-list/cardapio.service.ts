import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { EntityService } from "../../../../../node_modules/cardappio-component-hub/projects/cardappio-component-hub/src/public-api";

@Injectable({
  providedIn: 'root'
})
export class CardapioService extends EntityService<any, any> {

  constructor(http: HttpClient) {
    super(http);
    this.resource = 'http://localhost:8080/menus';
  }
}
