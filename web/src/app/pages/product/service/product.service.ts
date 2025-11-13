import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { environment } from "../../../../environments/environment";
import { EntityService } from "../../../entity-service/entity.service";

@Injectable({
  providedIn: 'root'
})
export class ProductService extends EntityService<any, any> {

  override resource: string = `${environment.apiUrl}/products`;

  constructor(public http: HttpClient) {
    super(http);
  }

  findToMenu(search: string): Observable<any> {
    const finalSearch = search ? `?search=${search}` : '';
    return this.http.get(`${this.resource}/to-menu${finalSearch}`);
  }

  findOptionsById(id: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.resource}/${id}/options`);
  }

  finalize(id: string) {
    return this.http.post<any>(`${this.resource}/${id}/finalize`, {});
  }

}
