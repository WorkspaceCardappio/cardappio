import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
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
  // productItemForm: FormGroup<any> = new FormGroup({});
  // additionalForm: FormGroup<any> = new FormGroup({});
  // productItemIngredientForm: FormGroup<any> = new FormGroup({});
  // productVariableForm: FormGroup<any> = new FormGroup({});
  isEdit = false;
  
  items: MenuItem[] = [];
  home: MenuItem = {};

  // productVariables: ProductVariable[] = [];
  // productItemIngredient: Ingredient[] = [];
  // additional: Additional[] = [];
  // itemSize: any[] = [];
  
  filteredCategories: any[] = [];
  // filteredProducts: any[] = [];
  // filteredIngredients: any[] = [];

  loading: boolean = false;
  currentIndex: number | null = null;

  // activeStep: number = 1;
  imagePreview: string | ArrayBuffer | null = null;
  
  constructor(
    private readonly builder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private categoryService: CategoryService,
    private productService: ProductService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.productForm = this.initForm();
    this.initBreadcrumb();
    this.checkRoute();
    // this.itemSize = [
    //   { code: 1, description: 'Único' },
    //   { code: 2, description: 'Pequeno' },
    //   { code: 3, description: 'Médio' },
    //   { code: 4, description: 'Grande' },
    // ];
    // this.productItemForm = this.buildProductItemForm();
    // this.productItemIngredientForm = this.buildProductItemIngredientForm();
    // this.additionalForm = this.buildAdditionalForm();
    // this.productVariableForm = this.buildProductVariableForm();
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
  private initForm() {
    const form = this.builder.group({
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
      productVariables: this.builder.array([]),
      productItemIngredient: this.builder.array([])
    });

    return form;
  }

  private loadProduct(id: string): void {
    this.productService.findById(id).subscribe({
      next: (product) => this.productForm.patchValue(product),
      error: () => this.navigateToList()
    });
  }

  // private buildProductItemForm() {
  //   const form = this.builder.group({
  //     id: [''],
  //     product: [null],
  //     quantity: [0],
  //     size: [null, Validators.required],
  //     description: [''],
  //     price: [0, Validators.required],
  //     active: [true, Validators.required],
  //     ingredients: this.builder.array([])
  //   });

  //   return form;

  // }

  // private buildProductItemIngredientForm() {
  //   const productItemIngredientForm = this.builder.group({
  //     id: [''],
  //     productItem: [null],
  //     quantity: [0, Validators.required],
  //     ingredient: ['', Validators.required],
  //   });

  //   return productItemIngredientForm;
  // }

  // private buildAdditionalForm() {
  //   return this.builder.group({
  //     id: [''],
  //     name: ['', Validators.required],
  //     price: [0, Validators.required]
  //   });
  // }

  // private buildProductVariableForm() {
  //   return this.builder.group({
  //     id: [''],
  //     name: [''],
  //     price: [0]
  //   });
  // }

  // protected addIngredient() {
  //   const ingredient = this.productForm.get('ingredient')?.getRawValue();
  //   const form = this.builder.group(ingredient);
  //   (this.productForm.get('ingredients') as FormArray).push(form);
  //   this.productForm.get('ingredient')?.setValue(null);
  // }
  
  // protected deleteIngredient(index: number) {
  //   (this.productForm.get('ingredients') as FormArray).removeAt(index);
  //   this.productItemIngredient.splice(index, 1);
  // }

  // protected addProductItem() {
   
  //   const productItemValue = this.productItemForm.getRawValue();
  //   const productItemIngredientValue = this.productItemIngredientForm.getRawValue();

  //   const productItemFA = this.productForm.get('productItem') as FormArray;
  //   const productItemIngredientFA = this.productForm.get('productItemIngredient') as FormArray;
  //   if (this.currentIndex !== null) {
  //     productItemFA.at(this.currentIndex).patchValue(productItemValue);
  //     this.currentIndex = null;
  //   } else {
  //     const newProductItem = this.builder.group(productItemValue)
  //     const newProductItemIngredient = this.builder.group(productItemIngredientValue)
  //     productItemFA.push(newProductItem);
  //     productItemIngredientFA.push(newProductItemIngredient);
  //   }
  //   this.productItemForm.reset();
  // }

  // protected deleteProductItem(index: number) {
  //   (this.productForm.get('productItem') as FormArray).removeAt(index);
  // }

  // protected addAdditional() {
  //   const additionalValue = this.additionalForm.getRawValue();
  //   const additionalFA = this.productForm.get('additional') as FormArray;
  //   if (this.currentIndex !== null) {
  //     additionalFA.at(this.currentIndex).patchValue(additionalValue);
  //     this.additional[this.currentIndex] = additionalValue;
  //     this.currentIndex = null;
  //   } else {
  //     const newAdditional = this.builder.group(additionalValue);
  //     additionalFA.push(newAdditional);
  //     this.additional.push(additionalValue);
  //   }
  //   this.additionalForm.reset();
  // }

  // protected deleteAdditional(index: number) {
  //   (this.productForm.get('additional') as FormArray).removeAt(index);
  //   this.additional.splice(index, 1);
  // }

  // protected addProductVariable() {
  //   const productVariable = this.productVariableForm.getRawValue();
  //   const pvFA = this.productForm.get('productVariables') as FormArray;
  //   if (this.currentIndex !== null) {
  //     pvFA.at(this.currentIndex).patchValue(productVariable);
  //     this.productVariables[this.currentIndex] = productVariable;
  //     this.currentIndex = null;
  //   } else {
  //     const newProductVariable = this.builder.group(productVariable);
  //     pvFA.push(newProductVariable);
  //     this.productVariables.push(productVariable);
  //   }
  //   this.productVariableForm.reset();
  // }

  // protected deleteProductVariable(index: number) {
  //   (this.productForm.get('productVariables') as FormArray).removeAt(index);
  //   this.productVariables.splice(index, 1);
  // }

  onCancel(): void {
    this.navigateToList();
  }

  // get ingredients(): FormArray {
  //   return this.productForm.get('ingredients') as FormArray;
  // }

  // onSave(): void {
  //   if (this.productForm.invalid) return;
  //   if (this.isEdit) {
  //     this.updateProduct(this.productForm.get('id')?.value);
  //   } else {
  //     this.createProduct();
  //   }
  // }

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
      complete: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  // searchProducts(event: any) {
  //   const query = event.query;
  //   const searchs = [];

  //   if (query) {
  //     searchs.push(`name=ilike=${query}%`);
  //   }

  //   const idsProduct = (this.productForm.get('products') as FormArray)
  //     .value
  //     .map((value: any) => value.product.id)
  //     .filter((value: any) => value !== '');
    
  //   if (idsProduct.length) {
  //     const ids = idsProduct.join(',');
  //     searchs.push(`id=out=${ids}`);
  //   }

  //   this.productService.findAll(20, searchs.join(';')).subscribe({
  //     next: (data) => {
  //       this.filteredProducts = data;
  //     },
  //     error: (err) => {
  //       console.error('Erro ao buscar produtos', err);
  //     },
  //     complete: () => {
  //       this.loading = false;
  //     },
  //   });
  // }

  // searchIngredients(event: any) {

  //   const query = event.query;
  //   const searchs = [];

  //   const ingredients = this.getFieldArray('ingredients');

  //   if (ingredients.length) {
  //     searchs.push(`id=out=(${ingredients.value.map((i: any) => i.id).join(',')})`)
  //   }

  //   this.productForm.get('ingredit')

  //   if (query)
  //     searchs.push(`name=ilike=${query}%`);

  //   this.ingredientService.findAll(20, searchs.join(';')).subscribe({
  //     next: (data) => { this.filteredIngredients = data; },
  //     error: (err) => { console.error('Erro ao buscar ingredientes', err); },
  //     complete: () => {
  //       this.loading = false;
  //       this.cdr.markForCheck();
  //     },
  //   });
  // }


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

  private getFieldArray(field: string) {
    return (this.productForm.get(field) as FormArray)
  }

  protected validFirstStep() {
    return !this.productForm.get('name')?.value
        || !this.productForm.get('category')?.value;
  }
}
