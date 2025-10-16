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
import { Ingredient } from '../../../model/ingredient';
import { Product } from '../../../model/product';
import { ProductVariable } from '../../../model/product_variable';
import { CategoryService } from '../../category/service/category.service';
import { IngredientService } from '../../ingredient/service/ingredient.service';
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
  items: MenuItem[] = [];
  productVariables: ProductVariable[] = [];
  ingredients: Ingredient[] = [];
  home: MenuItem = {};
  filteredCategories: any[] = [];
  filteredProducts: any[] = [];
  filteredIngredients: any[] = [];
  loading: boolean = false;
  imagePreview: string | ArrayBuffer | null = null;
  
  constructor(
    private readonly builder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private productService: ProductService,
    private ingredientService: IngredientService

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
      image: [null],
      category: [null, Validators.required],
      parent: [null],
      additionalPrice: [null, Validators.required],
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
      additionalPrice: formValue.additionalPrice,
      note: formValue.note,
      productVariables: this.productVariables,
      ingredients: this.ingredientService,
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

    this.ingredientService.findAll(20, searchs.join(';')).subscribe({
      next: (data: any) => {
        this.filteredIngredients = data;
      },
      error: (err) => {
        console.error('Erro ao buscar ingredientes', err);
      },
      complete: () => {
        this.loading = false;
      },
    });
  }
  
  addProductVariable() {
    const name: ProductVariable = this.form.get('product_variable')?.value?.trim();
    if (name) {
      this.productVariables.push(name);
      this.form.get('product_variable')?.reset();
    }
  }

  editProductVariable(variable: any) {
    this.form.get('product_variable')?.setValue(variable.name);
  }
  
  deleteProductVariable(variable: any) {
    this.productVariables = this.productVariables.filter(v => v !== variable);
  }

  addIngredient() {
    const ingredient: Ingredient = this.form.get('ingredient')?.value.trim();

    if (ingredient) {
      this.ingredients.push(ingredient);
    }
  }

  onFileSelected(event: any) {
    const file = event.files?.[0];
    if (file) {
      this.form.get('image')?.setValue(file);
    }
  const reader = new FileReader();
    reader.onload = () => {
    this.imagePreview = reader.result;
    };
  reader.readAsDataURL(file);
  }
}
