import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { EntityService } from "cardappio-component-hub";
<<<<<<< HEAD:web/src/app/screens/cardapio/cardapio-list/cardapio.service.ts
=======
import { HttpClient } from "@angular/common/http";
import { environment } from "../../../../environments/environment";
>>>>>>> f567f0ce0b6a738e14b77bd115e9de2b42c42bb3:web/src/app/pages/cardapio/service/cardapio.service.ts

@Injectable({
  providedIn: 'root'
})
export class CardapioService extends EntityService<any, any> {

  override resource: string = `${environment.apiUrl}/menus`;

  constructor(public http: HttpClient) {
    super(http);
  }
}
