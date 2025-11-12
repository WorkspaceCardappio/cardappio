import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputNumber } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { MultiSelectModule } from 'primeng/multiselect';
import { SelectModule } from 'primeng/select';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { Product } from '../../../model/product';
import LoadUtils from '../../../utils/load-utils';
import { RequestUtils } from '../../../utils/request-utils';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'app-product',
  imports: [
    TableModule,
    TagModule,
    IconFieldModule,
    InputTextModule,
    InputIconModule,
    MultiSelectModule,
    SelectModule,
    HttpClientModule,
    CommonModule,
    InputNumber,
    FormsModule,
    ButtonModule,
    RouterLink,
  ],
  providers: [ProductService],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss',
})
export class ProductComponent implements OnInit {
  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [{ label: 'Produtos', routerLink: '/product' }];
  products: Product[] = [];
  totalRecords: number = 0;
  loading: boolean = true;

  constructor(
    public service: ProductService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadProducts(LoadUtils.getDefault());
  }

  clear(table: any) {
    table.clear();
  }

  onEdit(id: any) {
    this.router.navigate([`product`, id]);
  }
  onDelete(id: any) {
    this.service.delete(id).subscribe(() =>
      this.loadProducts({
        first: 0,
        rows: 20,
        sortField: '',
        sortOrder: 1,
        filters: {},
      })
    );
  }

  loadProducts(event: TableLazyLoadEvent) {
    this.loading = true;

    let request = RequestUtils.build(event);
    request += `&search=saveStatus==FINALIZED`;

    this.service.findAllDTO(request).subscribe({
      next: (response) => {
        this.products = response.content;
        this.totalRecords = response.totalElements;
      },
      error: () => {},
      complete: () => {
        this.loading = false;
        this.cdr.detectChanges();
      },
    });
  }
}
