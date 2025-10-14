import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputNumber } from "primeng/inputnumber";
import { InputTextModule } from 'primeng/inputtext';
import { MultiSelectModule } from 'primeng/multiselect';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { Product } from '../../../model/product';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'app-product',
  imports: [
    TableModule, TagModule, IconFieldModule, InputTextModule, InputIconModule, MultiSelectModule, SelectModule, HttpClientModule, CommonModule,
    InputNumber, FormsModule, ButtonModule, RouterLink
],
  providers: [ ProductService ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductComponent {
  
  products: Product[] = [];
  loading: boolean = true;

  constructor(public service: ProductService) { }

  ngOnInit() {
    this.service.findAll();
    this.loading = false;
  }

}