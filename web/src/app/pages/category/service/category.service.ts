import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub';
import { environment } from '../../../../environments/environment';
import { Category } from '../model/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService extends EntityService<Category, string> {

  override resource: string = `${environment.apiUrl}/categories`;

  constructor(public http: HttpClient) {
    super(http);
   }
}
