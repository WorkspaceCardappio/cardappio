import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { EntityService } from '../../../entity-service/entity.service';
import { Ingredient } from '../../../model/ingredient';

@Injectable({
  providedIn: 'root'
})
export class IngredientService extends EntityService<Ingredient, string>  {

  override resource: string = `${environment.apiUrl}/ingredients`

  constructor(public http: HttpClient) {
    super(http);
  }
  
  getUnitOfMeasurement(): Observable<{ code: string; description: string;  typeValue: string}[]> {
    return this.http.get<{ code: string; description: string; typeValue: string }[]>
      (`${this.resource}/unity-of-measurement`)
  }
}
