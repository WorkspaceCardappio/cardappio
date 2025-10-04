import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub';
import { Observable } from 'rxjs';
import { Ingredient } from '../model/ingredient';

@Injectable({
  providedIn: 'root'
})
export class IngredientService extends EntityService<Ingredient, string>  {

  override resource: string = 'http://localhost:8080/ingredients'

  constructor(public http: HttpClient) {
    super(http);
  }
  
  getUnitOfMeasurement(): Observable<{ code: string; description: string;  typeValue: string}[]> {
    return this.http.get<{ code: string; description: string; typeValue: string }[]>
      (`${this.resource}/unity-of-measurement`)
  }
}
