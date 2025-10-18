import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { EntityService } from '../../../entity-service/entity.service';
import { Product } from '../../../model/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService extends EntityService<Product, string> {

  override resource: string = `${environment.apiUrl}/products`

  constructor(public http: HttpClient) {
    super(http);
  }
  
  findToMenu(search: string): Observable<any> {
        const finalSearch = search ? `?search=${search}` : '';
        return this.http.get(`${this.resource}/to-menu${finalSearch}`);
      }    
}
