import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { EntityService } from "cardappio-component-hub";
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProductService extends EntityService<any, any> {

  override resource: string = `${environment.apiUrl}/products`;

  constructor(public http: HttpClient) {
    super(http);
  }

}
