import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub';
import { Ingredient } from '../model/ingredient';

@Injectable({
  providedIn: 'root'
})
export class IngredientService extends EntityService<Ingredient, string>  {

  override resource: string = 'http://localhost:8080/ingredients'

  constructor(public http: HttpClient) {
    super(http);
  }
  
}
