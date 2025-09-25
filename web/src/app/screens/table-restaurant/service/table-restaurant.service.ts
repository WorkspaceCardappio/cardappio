import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub';
import { Category } from '../model/category';

@Injectable({
  providedIn: 'root',
})
export class TableRestaurantService extends EntityService<Category, string> {

  override resource: string = 'http://localhost:8080/categories';

  constructor(public http: HttpClient) {
    super(http);
  }
}
