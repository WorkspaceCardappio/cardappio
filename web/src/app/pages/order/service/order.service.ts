import { HttpClient } from "@angular/common/http";
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { environment } from "../../../../environments/environment";
import { EntityService } from "../../../entity-service/entity.service";
import { Page } from "../../../model/page.model";

@Injectable({
  providedIn: 'root'
})
export class OrderService extends EntityService<any, any> {

  override resource: string = `${environment.apiUrl}/orders`;

  constructor(public http: HttpClient) {
    super(http);
  }

  findToTicket(id: string, params: string): Observable<Page<any>> {
    return this.http.get<Page<any>>(`${this.resource}/to-ticket/${id}?${params}`);
  }

  findToSummary(id: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.resource}/items/to-summary/${id}`);
  }

  updateNoteInProductOrder(id: string, body: any) {
    return this.http.put<any>(`${this.resource}/product/${id}/note`, body);
  }

  updateNoteInOrder(id: string, body: any) {
    return this.http.put<any>(`${this.resource}/${id}/note`, body);
  }

  deleteProductInOrder(id: string) {
    return this.http.delete<any>(`${this.resource}/product/${id}`);
  }

  finalize(id: string) {
    return this.http.post<void>(`${this.resource}/${id}/finalize`, {});
  }

  findTotalByIds(ids: string[]) {
    return this.http.post<any>(`${this.resource}/total-by-ids`, { ids: ids });
  }

}
