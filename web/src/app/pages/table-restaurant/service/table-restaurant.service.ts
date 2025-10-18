import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { EntityService } from '../../../entity-service/entity.service';
import { TableRestaurant } from '../model/table-restaurant.type';

@Injectable({
  providedIn: 'root'
})
export class TableRestaurantService extends EntityService<TableRestaurant, string> {
  override resource: string = `${environment.apiUrl}/tables-restaurant`;

  constructor(public http: HttpClient) {
    super(http);
  }

  findToTicket(search: string): Observable<any> {
    return this.http.get(`${this.resource}/to-ticket?search=${search}`);
  }
}
