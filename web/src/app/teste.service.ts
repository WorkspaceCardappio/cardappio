import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub';

@Injectable({
  providedIn: 'root'
})
export class TesteService extends EntityService<any, any> {

  constructor(http: HttpClient) {
    super(http);
   }
}
