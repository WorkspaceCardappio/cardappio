import { Component } from '@angular/core';
import { CardappioListComponent } from 'cardappio-component-hub';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'app-product',
  imports: [CardappioListComponent],
  providers: [ ProductService ],
  templateUrl: './product.component.html',
  styleUrl: './product.component.scss'
})
export class ProductComponent {
  constructor(public service: ProductService){}
}
