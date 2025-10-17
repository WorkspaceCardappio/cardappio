import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { EntityService } from '../../../entity-service/entity.service';
import { Category } from '../../../model/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService extends EntityService<Category, string> {

  override resource: string = `${environment.apiUrl}/categories`;

  constructor(public http: HttpClient) {
    super(http);
   }
}
