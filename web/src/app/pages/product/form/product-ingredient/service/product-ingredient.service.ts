import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../../../environments/environment';
import { EntityService } from '../../../../../entity-service/entity.service';

@Injectable({
  providedIn: 'root'
})
export class ProductIngredientService extends EntityService<any, any>{

  override resource: string = `${environment.apiUrl}/product-ingredients`;

  constructor(public http: HttpClient) {
    super(http);
  }

  createProductIngredient(body: any[]) {
    return this.http.post(`${this.resource}/ingredients`, body);
  }

  getProductIngredient(id: string) {
    return this.http.get(`${this.resource}/ingredients/by-product/${id}`)
  }
  
}
