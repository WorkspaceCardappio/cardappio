import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../../../environments/environment';
import { EntityService } from '../../../../../entity-service/entity.service';

@Injectable({
  providedIn: 'root'
})
export class ProductItemService extends EntityService<any, any> {

  override resource: string = `${environment.apiUrl}/product-item`;

  constructor(public http: HttpClient) {
    super(http);
  }

  createProductItem(body: any[]) {
    return this.http.post(`${this.resource}/persist`, body);
  }
}
