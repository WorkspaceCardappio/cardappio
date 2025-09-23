import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EntityService } from 'cardappio-component-hub';
import { Product } from '../model/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService extends EntityService<Product, string> {

  override resource: string = 'http://localhost:8080/products'

  constructor(public http: HttpClient) {
    super(http);
  }
  
}
