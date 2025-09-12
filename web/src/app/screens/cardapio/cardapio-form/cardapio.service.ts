import { Injectable } from '@angular/core';
import { EntityService } from "cardappio-component-hub/lib/entity/entity.service";
import { HttpClient } from "@angular/common/http";

export interface User {
  id: number;
  name: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class CardapioService extends EntityService<any, any> {


  constructor(http: HttpClient) {
    super(http);
  }

}

