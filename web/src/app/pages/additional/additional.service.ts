import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { environment } from "../../../environments/environment";
import { EntityService } from "../../entity-service/entity.service";
import { Page } from "../../model/page.model";

@Injectable({
  providedIn: 'root'
})
export class AdditionalService extends EntityService<any, any> {

  override resource: string = `${environment.apiUrl}/additionals`;

  constructor(public http: HttpClient) {
    super(http);
  }

  findByProductIdToOrder(id: string): Observable<Page<any>> {
    return this.http.get<Page<any>>(`${this.resource}/product/${id}`);
  }

}
