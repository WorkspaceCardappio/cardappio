import { Injectable } from '@angular/core';
import { EntityService } from '../../../entity-service/entity.service';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Stock } from '../../../model/stock';

@Injectable({
  providedIn: 'root'
})
export class StockService extends EntityService<Stock, string> {

  override resource: string = `${environment.apiUrl}/stocks`

  constructor(public http: HttpClient) {
    super(http);
  }
}
