import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { DatePickerModule } from 'primeng/datepicker';
import { FieldsetModule } from 'primeng/fieldset';
import { FileUploadModule } from 'primeng/fileupload';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { StepperModule } from 'primeng/stepper';
import { TableModule } from "primeng/table";
import { TextareaModule } from 'primeng/textarea';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { Product } from '../../../model/product';
import { ProductVariable } from '../../../model/product_variable';
import { CategoryService } from '../../category/service/category.service';
import { ProductService } from '../service/product.service';



@Component({
  selector: 'app-product-form',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    BreadcrumbModule,
    FieldsetModule,
    InputTextModule,
    InputNumberModule,
    DatePickerModule,
    ToggleSwitchModule,
    AutoCompleteModule,
    FileUploadModule,
    TextareaModule,
    StepperModule,
    ButtonModule,
    TableModule
],
  providers: [
    CategoryService,
    ProductService
  ],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.scss'
})
export class ProductFormComponent implements OnInit {
  
  form: FormGroup<any> = new FormGroup({});
  date1: Date | undefined;
  items: MenuItem[] = [];
  productVariables: ProductVariable[] = [];
  home: MenuItem = {};
  filteredCategories: any[] = [];
  filteredProducts: any[] = [];
  filteredIngredients: any[] = [];
  loading: boolean = false;
  
  constructor(
    private readonly builder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private productService: ProductService

    // private ingredientService: IngredientService

  ) { }
  
  ngOnInit(): void {
    this.initForm();
    this.initBreadcrumb();
    this.checkRoute();
  };

  private initForm(): void {
    this.form = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      quantity: [null, Validators.required],
      price: [null, Validators.required],
      expirationDate: [null, Validators.required],
      description: [''],
      active: [true],
      image: [''],
      category: [null, Validators.required],
      parent: [null],
      note: [''],
      product_variable: [''],
      ingredient: [null]
    })
  }
  
  private initBreadcrumb(): void {
    this.items = [
      { label: 'Produtos', routerLink: '/product' },
      { label: 'Novo Produto' }
    ];
    this.home = { icon: 'pi pi-home', routerLink: '/' };
  }

  private checkRoute() {
    const { id } = this.route.snapshot.params;
    if (id != 'new') {
      this.loadProduct(id);
    }
  }

  private loadProduct(id: string) {
    this.productService.findById(id).subscribe((product) => {
      this.form.patchValue(product);
    });
  }

  create(): void {
    if (this.form.invalid) {
      return;
    }
    const { id } = this.route.snapshot.params;
    const formValue = this.form.value;
    const product: Product = {
      id: formValue.id,
      name: formValue.name,
      quantity: formValue.quantity,
      price: formValue.price,
      expirationDate: formValue.expirationDate,
      description: formValue.description,
      active: formValue.active,
      image: formValue.image,
      category: formValue.category,
      parent: formValue.parent,
      note: formValue.note,
      productVariables: this.productVariables,
  };
    if (id != 'new') {
      this.productService
        .update(id, product)
        .subscribe(() => this.router.navigate(['category']));
    } else {
      this.productService
        .create(product)
        .subscribe(() => this.router.navigate(['category']));
    }
    console.log(product);
  }

  cancel() {
    this.router.navigate(['product']);
  }

  searchCategories(event: any) {
    const query = event.query;
    const searchs = [];

    if (query) {
      searchs.push(`name=ilike=${query}%`);
    }

    const id = this.form.get('id')?.value;
    if (id) {
      searchs.push(`id=out=${id}`);
    }

    this.categoryService.findAll(20, searchs.join(';')).subscribe({
      next: (data) => {
        this.filteredCategories = data;
      },
      error: (err) => {
        console.error('Erro ao buscar categorias', err);
      },
      complete: () => {
        this.loading = false;
      },
    });
  }

    searchProducts(event: any) {
    const query = event.query;
    const searchs = [];

    if (query) {
      searchs.push(`name=ilike=${query}%`);
    }

    const id = this.form.get('id')?.value;
    if (id) {
      searchs.push(`id=out=${id}`);
    }

    this.productService.findAll(20, searchs.join(';')).subscribe({
      next: (data) => {
        this.filteredCategories = data;
      },
      error: (err) => {
        console.error('Erro ao buscar produtos', err);
      },
      complete: () => {
        this.loading = false;
      },
    });
    }
  
   searchIngredients(event: any) {
    const query = event.query;
    const searchs = [];

    if (query) {
      searchs.push(`name=ilike=${query}%`);
    }

    const id = this.form.get('id')?.value;
    if (id) {
      searchs.push(`id=out=${id}`);
    }

    // this.service.findAll(20, searchs.join(';')).subscribe({
    //   next: (data) => {
    //     this.filteredCategories = data;
    //   },
    //   error: (err) => {
    //     console.error('Erro ao buscar produtos', err);
    //   },
    //   complete: () => {
    //     this.loading = false;
    //   },
    // });
    }
  
  addProductVariable() {
  const name = this.form.get('product_variable')?.value?.trim();
  if (name) {
    this.productVariables.push({ name } as ProductVariable);
    this.form.get('product_variable')?.reset();
  }
  }
  editProductVariable(variable: any) {
  this.form.get('product_variable')?.setValue(variable.name);
}
  deleteProductVariable(variable: any) {
  this.productVariables = this.productVariables.filter(v => v !== variable);
}
}
