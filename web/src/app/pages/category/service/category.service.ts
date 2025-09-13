import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub';


@Injectable({
  providedIn: 'root'
})
export class CategoryService extends EntityService<any, any> {

  override resource: string = 'http://localhost:8080/categories';

  constructor(http: HttpClient) {
    super(http);
  }
}
