import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { environment } from "../../../../environments/environment";
import { EntityService } from "../../../entity-service/entity.service";

@Injectable({
  providedIn: 'root'
})
export class MenuService extends EntityService<any, any> {

  override resource: string = `${environment.apiUrl}/menus`;

  constructor(public http: HttpClient) {
    super(http);
  }

  findProductsInMenu(id: string): Observable<any> {
    return this.http.get(`${this.resource}/${id}/products`);
  }
}
