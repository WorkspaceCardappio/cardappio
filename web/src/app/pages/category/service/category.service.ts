import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub';
import { UUID } from 'crypto';
import { Category } from '../model/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService extends EntityService<Category, UUID> {

  override resource: string = 'http://localhost:8080/categories';

  constructor(public http: HttpClient) {
    super(http);
   }  
}