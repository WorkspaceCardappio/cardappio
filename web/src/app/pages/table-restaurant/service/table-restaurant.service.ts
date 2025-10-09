import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub';
import { environment } from '../../../../environments/environment';
import { TableRestaurant } from '../model/table-restaurant.type';

@Injectable()
export class TableRestaurantService extends EntityService<TableRestaurant, string> {

  override resource: string = `${ environment.apiUrl }/tables-restaurant`;

  constructor(public http: HttpClient) {
    super(http);
  }
}
