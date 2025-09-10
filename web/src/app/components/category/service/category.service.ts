import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub/lib/entity/entity.service';


@Injectable({
  providedIn: 'root'
})
export class CategoryService extends EntityService<any, any> {

  constructor(http: HttpClient) {
    super(http);
    this.resource = 'http://localhost:8080/categories';
  }
}
