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
import { SelectModule } from 'primeng/select';
import { StepperModule } from 'primeng/stepper';
import { TableModule } from "primeng/table";
import { TextareaModule } from 'primeng/textarea';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { Additional } from '../../../model/additional';
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
    TableModule,
    SelectModule
  ],
  providers: [
    CategoryService,
    ProductService
  ],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.scss'
})
export class ProductFormComponent implements OnInit {

  productForm: FormGroup<any> = new FormGroup({});
  productItemForm: FormGroup<any> = new FormGroup({});
  additionalForm: FormGroup<any> = new FormGroup({});
  productItemIngredientForm: FormGroup<any> = new FormGroup({});
  productVariableForm: FormGroup<any> = new FormGroup({});
  isEdit = false;
  
  items: MenuItem[] = [];
  home: MenuItem = {};

  productVariables: ProductVariable[] = [];
  productItemIngredient: Ingredient[] = [];
  additional: Additional[] = [];
  itemSize: any[] = [];
  
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
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.initBreadcrumb();
    this.checkRoute();
    this.itemSize = [
      { code: 1, description: 'Único' },
      { code: 2, description: 'Pequeno' },
      { code: 3, description: 'Médio' },
      { code: 4, description: 'Grande' },
    ];
    this.productItemIngredientForm = this.buildProductItemIngredientForm();
    this.productItemForm = this.buildProductItemForm();
    this.additionalForm = this.buildAdditionalForm();
    this.productVariableForm = this.buildProductVariableForm();
  }

  private initForm(): void {
    this.productForm = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      description: [''],
      expirationDate: [null],
      image: [null],
      note: [''],
      category: [null, Validators.required],
      productItem: this.builder.array([]),
      ingredients: this.builder.array([]),
      additional: this.builder.array([]),
      productVariables: this.builder.array([])
    });
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
      next: (product) => this.productForm.patchValue(product),
      error: () => this.navigateToList()
    });
  }

  private buildProductItemIngredientForm() {
    return this.builder.group({
      id: [''],
      productItem: [null],
      quantity: [0, Validators.required],
      ingredient: ['', Validators.required],
    });
  }

  protected addProductItemIngredient() {
    const ingredientValue = this.productItemIngredientForm.getRawValue();
    const ingredientsFA = this.productForm.get('ingredients') as FormArray;
    if (this.currentIndex !== null) {
      ingredientsFA.at(this.currentIndex).patchValue(ingredientValue);
      this.productItemIngredient[this.currentIndex] = ingredientValue;
      this.currentIndex = null;
    } else {
      const newIngredientForm = this.builder.group(ingredientValue);
      ingredientsFA.push(newIngredientForm);
      this.productItemIngredient.push(ingredientValue);
    }
    this.productItemIngredientForm.reset();
    this.productItemIngredientForm = this.buildProductItemIngredientForm();
  }

  protected deleteIngredient(index: number) {
    (this.productForm.get('ingredients') as FormArray).removeAt(index);
    this.productItemIngredient.splice(index, 1);
  }

  private buildProductItemForm() {
    return this.builder.group({
      id: [''],
      product: [null],
      quantity: [0],
      size: [null, Validators.required],
      price: [0, Validators.required],
      active: [true, Validators.required],
      ingredients: [null, Validators.required]
    });
  }

  protected addProductItem() {
    const productItemValue = this.productItemForm.getRawValue();
    const productItemFA = this.productForm.get('productItem') as FormArray;
    if (this.currentIndex !== null) {
      productItemFA.at(this.currentIndex).patchValue(productItemValue);
      this.currentIndex = null;
    } else {
      const newProductItem = this.builder.group(productItemValue);
      productItemFA.push(newProductItem);
    }
    this.productItemForm.reset();
    this.productItemForm = this.buildProductItemForm();
  }

  protected deleteProductItem(index: number) {
    (this.productForm.get('productItem') as FormArray).removeAt(index);
  }

  private buildAdditionalForm() {
    return this.builder.group({
      id: [''],
      name: ['', Validators.required],
      price: [0, Validators.required]
    });
  }

  protected addAdditional() {
    const additionalValue = this.additionalForm.getRawValue();
    const additionalFA = this.productForm.get('additional') as FormArray;
    if (this.currentIndex !== null) {
      additionalFA.at(this.currentIndex).patchValue(additionalValue);
      this.additional[this.currentIndex] = additionalValue;
      this.currentIndex = null;
    } else {
      const newAdditional = this.builder.group(additionalValue);
      additionalFA.push(newAdditional);
      this.additional.push(additionalValue);
    }
    this.additionalForm.reset();
    this.additionalForm = this.buildAdditionalForm();
  }

  protected deleteAdditional(index: number) {
    (this.productForm.get('additional') as FormArray).removeAt(index);
    this.additional.splice(index, 1);
  }

  private buildProductVariableForm() {
    return this.builder.group({
      id: [''],
      name: [''],
      price: [0]
    });
  }

  protected addProductVariable() {
    const productVariable = this.productVariableForm.getRawValue();
    const pvFA = this.productForm.get('productVariables') as FormArray;
    if (this.currentIndex !== null) {
      pvFA.at(this.currentIndex).patchValue(productVariable);
      this.productVariables[this.currentIndex] = productVariable;
      this.currentIndex = null;
    } else {
      const newProductVariable = this.builder.group(productVariable);
      pvFA.push(newProductVariable);
      this.productVariables.push(productVariable);
    }
    this.productVariableForm.reset();
    this.productVariableForm = this.buildProductVariableForm();
  }

  protected deleteProductVariable(index: number) {
    (this.productForm.get('productVariables') as FormArray).removeAt(index);
    this.productVariables.splice(index, 1);
  }

  onCancel(): void {
    this.navigateToList();
  }

  onSave(): void {
    if (this.productForm.invalid) return;
    if (this.isEdit) {
      this.updateProduct(this.productForm.get('id')?.value);
    } else {
      this.createProduct();
    }
  }

  private createProduct(): void {
    const { id, ...productData } = this.productForm.value;
    this.productService.create(productData).subscribe({
      next: () => this.navigateToList()
    });
  }

  private updateProduct(id: string): void {
    this.productService.update(id, this.productForm.value).subscribe({
      next: () => this.navigateToList()
    });
  }

  searchCategories(event: any) {
    const query = event.query;
    const searchs = [];
    if (query) searchs.push(`name=ilike=${query}%`);
    this.categoryService.findAll(20, searchs.join(';')).subscribe({
      next: (data) => { this.filteredCategories = data; },
      error: (err) => { console.error('Erro ao buscar categorias', err); },
      complete: () => { this.loading = false; },
    });
  }

  searchProducts(event: any) {
    const query = event.query;
    const searchs = [];

    if (query) {
      searchs.push(`name=ilike=${query}%`);
    }

    const idsProduct = (this.productForm.get('products') as FormArray)
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
    if (query) searchs.push(`name=ilike=${query}%`);
    this.ingredientService.findAll(20, searchs.join(';')).subscribe({
      next: (data) => { this.filteredCategories = data; },
      error: (err) => { console.error('Erro ao buscar ingredientes', err); },
      complete: () => { this.loading = false; },
    });
  }

  protected editIngredient(index: number): void {
  const ingredientValue = (this.productForm.get('ingredients') as FormArray).at(index);
  if (!ingredientValue) return;
  this.productItemIngredientForm.patchValue(ingredientValue.value);
  this.currentIndex = index;
  }

  protected editAdditional(index: number): void {
    const additionalValue = (this.productForm.get('additional') as FormArray).at(index);
    if (!additionalValue) return;
    this.additionalForm.patchValue(additionalValue.value);
    this.currentIndex = index;
  }

  protected editProductVariable(index: number): void {
    const productVariableValue = (this.productForm.get('productVariables') as FormArray).at(index);
    if (!productVariableValue) return;
    this.productVariableForm.patchValue(productVariableValue.value);
    this.currentIndex = index;
  }

  protected navigateToList(): void {
    this.router.navigate(['/product']);
  }

  onFileSelected(event: any) {
    const file = event.files?.[0];
    if (file) this.productForm.get('image')?.setValue(file);
    const reader = new FileReader();
    reader.onload = () => { this.imagePreview = reader.result; };
    reader.readAsDataURL(file);
  }
}
