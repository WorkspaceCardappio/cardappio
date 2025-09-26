import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub';
import { TableRestaurant } from '../model/table-restaurant.type';

@Injectable()
export class TableRestaurantService extends EntityService<TableRestaurant, string> {

  override resource: string = 'http://localhost:8080/tables';

  constructor(public http: HttpClient) {
    super(http);
  }
}
