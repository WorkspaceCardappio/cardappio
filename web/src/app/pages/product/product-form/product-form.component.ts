import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
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
  additionalForm: FormGroup<any> = new FormGroup({});
  ingredientForm: FormGroup<any> = new FormGroup({});
  productVariableForm: FormGroup<any> = new FormGroup({});
  isEdit = false;
  
  items: MenuItem[] = [];
  home: MenuItem = {};

  productVariables: ProductVariable[] = [];
  ingredients: Ingredient[] = [];
  
  filteredCategories: any[] = [];
  filteredProducts: any[] = [];
  filteredIngredients: any[] = [];

  loading: boolean = false;
  currentIndex: number | null = null;

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
    this.ingredientForm = this.buildIngredientForm();
    // this.additionalForm = this.buildAdditionalForm();
    // this.productVariableForm = this.buildProductVariableForm();
  };

  private initForm(): void {
    this.form = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      price: [null, Validators.required],
      quantity: [null, Validators.required],
      description: [''],
      active: [true],
      expirationDate: [null, Validators.required],
      image: [null],
      note: [''],
      category: [null, Validators.required],
      additional: this.builder.array([]),
      productVariables: this.builder.array([]),
      ingredients: this.builder.array([])
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
    this.isEdit = id !== 'new';

    if (this.isEdit) {
      this.loadProduct(id);
    }
  }

  private loadProduct(id: string): void {
    this.productService.findById(id).subscribe({
      next: (product) => this.form.patchValue(product),
      error: () => this.navigateToList()
    });
  }

  onCancel(): void {
    this.navigateToList();
  }

  onSave(): void {
    if (this.form.invalid) {
      return;
    }

    if (this.isEdit) {
      this.updateProduct(this.form.get('id')?.value);
    } else {
      this.createProduct();
    }
  }

  private createProduct(): void {
    const { id, ...productData } = this.form.value;

    this.productService.create(productData).subscribe({
      next: () => this.navigateToList()
    })
  }

  private updateProduct(id: string): void {
    this.productService.update(id, this.form.value).subscribe({
      next: () => this.navigateToList()
    })
  }

  searchCategories(event: any) {
    const query = event.query;
    const searchs = [];

    if (query) {
      searchs.push(`name=ilike=${query}%`);
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

    const idsProduct = (this.form.get('products') as FormArray)
      .value
      .map((value: any) => value.product.id)
      .filter((value: any) => value !== '');
    
    if (idsProduct.length) {
      const ids = idsProduct.join(',');
      searchs.push(`id=out=${ids}`);
    }

    this.productService.findAll(20, searchs.join(';')).subscribe({
      next: (data) => {
        this.filteredProducts = data;
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

    const idsIngredient = (this.form.get('ingredients') as FormArray)
      .value
      .map((value: any) => value.ingredient.id)
      .filter((value: any) => value !== '');
    
    if (idsIngredient) {
      const ids = idsIngredient.join(',');
      searchs.push(`id=out=${ids}`);
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

  protected addIngredient() {
    const ingredientValue = this.ingredientForm.getRawValue();

    if (this.currentIndex !== null) {
      (this.form.get('ingredients') as FormArray)
        .at(this.currentIndex)
        .patchValue(ingredientValue);
      this.currentIndex = null;
    } else {
      const newIngredientForm = this.builder.group(ingredientValue);
      (this.form.get('ingredients') as FormArray).push(newIngredientForm);
    }

    this.ingredientForm.reset();
    this.ingredientForm = this.buildIngredientForm();
  }

  protected editIngredient(index: number) {
    const ingredient = (this.form.get('ingredients') as FormArray).at(index);
    this.ingredientForm.patchValue(ingredient.value);
    this.currentIndex = index;
  }

  protected deleteIngredient(index: number) {
    (this.form.get('ingredients') as FormArray).removeAt(index);
  }

  protected clearIngredient() {
    this.ingredientForm = this.buildIngredientForm();
    this.currentIndex = null;
  }
  
  private buildIngredientForm() {
    const ingredientForm = this.builder.group({
      id: [''],
      ingredient: [null, Validators.required],
      quantity: [null, Validators.required],
      active: [true],
      unityOfMeasurement: [null, Validators.required],
      allergenic: [true]
    })
    ingredientForm.get('ingredients')?.valueChanges
      .subscribe((ingredient: any) => {
        if (!ingredient?.id)
          return;
        this.ingredientForm.get('quantity')?.setValue(ingredient.quantity);
      });
    return ingredientForm;
  }

  addProductVariable() {
    const name: ProductVariable = this.form.get('product_variable')?.value?.trim();
    if (name) {
      this.productVariables.push(name);
      this.form.get('productVariables')?.reset();
    }
  }

  editProductVariable(variable: any) {
    this.form.get('product_variable')?.setValue(variable.name);
  }
  
  deleteProductVariable(variable: any) {
    this.productVariables = this.productVariables.filter(v => v !== variable);
  }

  protected navigateToList(): void {
    this.router.navigate(['/product']);
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
