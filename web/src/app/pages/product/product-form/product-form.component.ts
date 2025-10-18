// import { CommonModule } from '@angular/common';
// import { Component, OnInit } from '@angular/core';
// import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
// import { ActivatedRoute, Router } from '@angular/router';
// import { MenuItem } from 'primeng/api';
// import { AutoCompleteModule } from 'primeng/autocomplete';
// import { BreadcrumbModule } from 'primeng/breadcrumb';
// import { ButtonModule } from 'primeng/button';
// import { DatePickerModule } from 'primeng/datepicker';
// import { FieldsetModule } from 'primeng/fieldset';
// import { FileUploadModule } from 'primeng/fileupload';
// import { InputNumberModule } from 'primeng/inputnumber';
// import { InputTextModule } from 'primeng/inputtext';
// import { SelectModule } from 'primeng/select';
// import { StepperModule } from 'primeng/stepper';
// import { TableModule } from "primeng/table";
// import { TextareaModule } from 'primeng/textarea';
// import { ToggleSwitchModule } from 'primeng/toggleswitch';
// import { Additional } from '../../../model/additional';
// import { Ingredient } from '../../../model/ingredient';
// import { ProductVariable } from '../../../model/product_variable';
// import { CategoryService } from '../../category/service/category.service';
// import { IngredientService } from '../../ingredient/service/ingredient.service';
// import { ProductService } from '../service/product.service';


// @Component({
//   selector: 'app-product-form',
//   imports: [
//     CommonModule,
//     ReactiveFormsModule,
//     FormsModule,
//     BreadcrumbModule,
//     FieldsetModule,
//     InputTextModule,
//     InputNumberModule,
//     DatePickerModule,
//     ToggleSwitchModule,
//     AutoCompleteModule,
//     FileUploadModule,
//     TextareaModule,
//     StepperModule,
//     ButtonModule,
//     TableModule,
//     SelectModule
//   ],
//   providers: [
//     CategoryService,
//     ProductService
//   ],
//   templateUrl: './product-form.component.html',
//   styleUrl: './product-form.component.scss'
// })
// export class ProductFormComponent implements OnInit {

//   productForm: FormGroup<any> = new FormGroup({});
//   productItemForm: FormGroup<any> = new FormGroup({});
//   additionalForm: FormGroup<any> = new FormGroup({});
//   productItemIngredientForm: FormGroup<any> = new FormGroup({});
//   productVariableForm: FormGroup<any> = new FormGroup({});
//   isEdit = false;
  
//   items: MenuItem[] = [];
//   home: MenuItem = {};

//   productVariables: ProductVariable[] = [];
//   productItemIngredient: Ingredient[] = [];
//   additional: Additional[] = [];
//   itemSize: any[] = [];
  
//   filteredCategories: any[] = [];
//   filteredProducts: any[] = [];
//   filteredIngredients: any[] = [];

//   loading: boolean = false;
//   currentIndex: number | null = null;

//   imagePreview: string | ArrayBuffer | null = null;
  
//   constructor(
//     private readonly builder: FormBuilder,
//     private router: Router,
//     private route: ActivatedRoute,
//     private categoryService: CategoryService,
//     private productService: ProductService,
//     private ingredientService: IngredientService

//   ) { }

//   ngOnInit(): void {
//     this.initForm();
//     this.initBreadcrumb();
//     this.checkRoute();
//     this.productItemIngredientForm = this.buildProductItemIngredientForm();
//     this.productItemForm = this.buildProductItemForm();
//     this.additionalForm = this.buildAdditionalForm();
//     this.productVariableForm = this.buildProductVariableForm();

//     this.itemSize = [
//       { code: 1, description: 'Único' },
//       { code: 2, description: 'Pequeno' },
//       { code: 3, description: 'Médio' },
//       { code: 4, description: 'Grande' },
//     ]
//   };

//   private initForm(): void {
//     this.productForm = this.builder.group({
//       id: [''],
//       name: ['', Validators.required],
//       description: [''],
//       expirationDate: [null],
//       image: [null],
//       note: [''],
//       category: [null, Validators.required],
//       productItem: this.builder.array([]),
//       ingredients: this.builder.array([]),
//       additional: this.builder.array([]),
//       productVariables: this.builder.array([])
//     });
//  }
  
//   private initBreadcrumb(): void {
//     this.items = [
//       { label: 'Produtos', routerLink: '/product' },
//       { label: 'Novo Produto' }
//     ];
//     this.home = { icon: 'pi pi-home', routerLink: '/' };
//   }

//   private checkRoute() {
//     const { id } = this.route.snapshot.params;
//     this.isEdit = id !== 'new';

//     if (this.isEdit) {
//       this.loadProduct(id);
//     }
//   }

//   private loadProduct(id: string): void {
//     this.productService.findById(id).subscribe({
//       next: (product) => this.productForm.patchValue(product),
//       error: () => this.navigateToList()
//     });
//   }

//   private buildProductItemIngredientForm() {
//     const ingredientForm = this.builder.group({
//       id: [''],
//       productItem: [null],
//       quantity: [0, Validators.required],
//       ingredient: ['', Validators.required],
//     })
//     ingredientForm.get('ingredients')?.valueChanges
//       .subscribe((ingredient: any) => {
//         if (!ingredient?.id)
//           return;
//         this.productItemIngredientForm.get('quantity')?.setValue(ingredient.quantity);
//       });
//     return ingredientForm;
//   }

//   protected addProductItemIngredient() {
//     const ingredientValue = this.productItemIngredientForm.getRawValue();

//     if (this.currentIndex !== null) {
//       (this.productForm.get('ingredients') as FormArray)
//         .at(this.currentIndex)
//         .patchValue(ingredientValue);
//       this.currentIndex = null;
//     } else {
//       const newIngredientForm = this.builder.group(ingredientValue);
//       (this.productForm.get('ingredients') as FormArray).push(newIngredientForm);
//     }

//     this.productItemIngredientForm.reset();
//     this.productItemIngredientForm = this.buildProductItemIngredientForm();
//   }

//   protected editIngredient(index: number) {
//     const ingredient = (this.productItemIngredientForm.get('ingredients') as FormArray).at(index);
//     this.productItemIngredientForm.patchValue(ingredient.value);
//     this.currentIndex = index;
//   }

//   protected deleteIngredient(index: number) {
//     (this.productForm.get('ingredients') as FormArray).removeAt(index);
//   }

//   protected clearIngredient() {
//     this.productItemIngredientForm = this.buildProductItemIngredientForm();
//     this.currentIndex = null;
//   }

//   private buildProductItemForm() {
//     const productItemForm = this.builder.group({
//       id: [''],
//       product: [null],
//       quantity: [0],
//       size: [this.itemSize[1], Validators.required],
//       price: [0, Validators.required],
//       active: [true, Validators.required],
//       ingredients: [null, Validators.required]
//     })
//     productItemForm.get('productItem')?.valueChanges
//       .subscribe((ingredient: any) => {
//         if (!ingredient?.id)
//           return;
//         this.productForm.get('productItem')?.setValue(productItemForm.value);
//       });
//     return productItemForm;
//   }

//   protected addProductItem() {
//     const productItemValue = this.productItemIngredientForm.getRawValue();

//     if (this.currentIndex !== null) {
//       (this.productForm.get('productItem') as FormArray)
//         .at(this.currentIndex)
//         .patchValue(productItemValue);
//       this.currentIndex = null;
//     } else {
//       const newProductItem = this.builder.group(productItemValue);
//       (this.productForm.get('productItem') as FormArray).push(newProductItem);
//     }

//     this.productItemForm.reset();
//     this.productItemForm = this.buildProductItemForm();
//   }

//   protected editProductItem(index: number) {
//     const productItem = (this.productForm.get('productItem') as FormArray).at(index);
//     this.productItemForm.patchValue(productItem.value);
//     this.currentIndex = index;
//   }

//   protected deleteProductItem(index: number) {
//     (this.productForm.get('productItem') as FormArray).removeAt(index);
//   }

//   protected clearProductItem() {
//     this.productItemForm = this.buildProductItemForm();
//     this.currentIndex = null;
//   }    
    
//   private buildAdditionalForm() {
//     const additionalForm = this.builder.group({
//       id: [''],
//       name: ['', Validators.required],
//       price: [0, Validators.required]
//     })
//     additionalForm.get('additional')?.valueChanges
//       .subscribe((additional: any) => {
//         if (!additional?.id)
//           return;
//         this.productForm.get('additional')?.setValue(additional.price);
//       });
//     return additionalForm;
//   }

//   protected addAdditional() {
//     const additionalValue = this.additionalForm.getRawValue();

//     if (this.currentIndex !== null) {
//       (this.productForm.get('additional') as FormArray)
//         .at(this.currentIndex)
//         .patchValue(additionalValue);
//       this.currentIndex = null;
//     } else {
//       const newAdditional = this.builder.group(additionalValue);
//       (this.productForm.get('additional') as FormArray).push(newAdditional);
//     }

//     this.additionalForm.reset();
//     this.additionalForm = this.buildAdditionalForm();
//   }

//   protected editAdditional(index: number) {
//     const additional = (this.productForm.get('additional') as FormArray).at(index);
//     this.additionalForm.patchValue(additional.value);
//     this.currentIndex = index;
//   }

//   protected deleteAdditional(index: number) {
//     (this.productForm.get('additional') as FormArray).removeAt(index);
//   }

//   protected clearAdditional() {
//     this.additionalForm = this.buildAdditionalForm();
//     this.currentIndex = null;
//   }

//   private buildProductVariableForm() {
//     const productVariableForm = this.builder.group({
//       id: [''],
//       name: [''],
//       price: [0]
//     })
//     productVariableForm.get('productVariables')?.valueChanges
//       .subscribe((productVariable: any) => {
//         if (!productVariable?.id)
//           return;
//         this.productVariableForm.get('name')?.setValue(productVariable.name);
//       });
//     return productVariableForm;
//   }

//   protected addProductVariable() {
//     const productVariable = this.productVariableForm.getRawValue();

//     if (this.currentIndex !== null) {
//       (this.productForm.get('productVariables') as FormArray)
//         .at(this.currentIndex)
//         .patchValue(productVariable);
//       this.currentIndex = null;
//     } else {
//       const newProductVariable = this.builder.group(productVariable);
//       (this.productForm.get('productVariables') as FormArray).push(newProductVariable);
//     }

//     this.productVariableForm.reset();
//     this.productVariableForm = this.buildProductVariableForm();
//   }

//   protected editProductVariable(index: number) {
//     const productVariable = (this.productForm.get('productVariables') as FormArray).at(index);
//     this.productVariableForm.patchValue(productVariable.value);
//     this.currentIndex = index;
//   }

//   protected deleteProductVariable(index: number) {
//     (this.productForm.get('productVariables') as FormArray).removeAt(index);
//   }

//   protected clearProductVariable() {
//     this.productVariableForm = this.buildProductVariableForm();
//     this.currentIndex = null;
//   }

//   onCancel(): void {
//     this.navigateToList();
//   }

//   onSave(): void {
//     if (this.productForm.invalid) {
//       return;
//     }

//     if (this.isEdit) {
//       this.updateProduct(this.productForm.get('id')?.value);
//     } else {
//       this.createProduct();
//     }
//   }

//   private createProduct(): void {
//     const { id, ...productData } = this.productForm.value;
//     this.productService.create(productData).subscribe({
//       next: () => this.navigateToList()
//     })
//   }

//   private updateProduct(id: string): void {
//     this.productService.update(id, this.productForm.value).subscribe({
//       next: () => this.navigateToList()
//     })
//   }

//   searchCategories(event: any) {
//     const query = event.query;
//     const searchs = [];

//     if (query) {
//       searchs.push(`name=ilike=${query}%`);
//     }

//     this.categoryService.findAll(20, searchs.join(';')).subscribe({
//       next: (data) => {
//         this.filteredCategories = data;
//       },
//       error: (err) => {
//         console.error('Erro ao buscar categorias', err);
//       },
//       complete: () => {
//         this.loading = false;
//       },
//     });
//   }

//   searchProducts(event: any) {
//     const query = event.query;
//     const searchs = [];

//     if (query) {
//       searchs.push(`name=ilike=${query}%`);
//     }

//     const idsProduct = (this.productForm.get('products') as FormArray)
//       .value
//       .map((value: any) => value.product.id)
//       .filter((value: any) => value !== '');
    
//     if (idsProduct.length) {
//       const ids = idsProduct.join(',');
//       searchs.push(`id=out=${ids}`);
//     }

//     this.productService.findAll(20, searchs.join(';')).subscribe({
//       next: (data) => {
//         this.filteredProducts = data;
//       },
//       error: (err) => {
//         console.error('Erro ao buscar produtos', err);
//       },
//       complete: () => {
//         this.loading = false;
//       },
//     });
//   }
  
//   searchIngredients(event: any) {
//     const query = event.query;
//     const searchs = [];

//     if (query) {
//       searchs.push(`name=ilike=${query}%`);
//     }

//     const idsIngredient = (this.productItemIngredientForm.get('ingredients') as FormArray)
//       .value
//       .map((value: any) => value.ingredient.id)
//       .filter((value: any) => value !== '');
    
//     if (idsIngredient) {
//       const ids = idsIngredient.join(',');
//       searchs.push(`id=out=${ids}`);
//     }

//     this.ingredientService.findAll(20, searchs.join(';')).subscribe({
//       next: (data: any) => {
//         this.filteredIngredients = data;
//       },
//       error: (err) => {
//         console.error('Erro ao buscar ingredientes', err);
//       },
//       complete: () => {
//         this.loading = false;
//       },
//     });
//   }

//   protected navigateToList(): void {
//     this.router.navigate(['/product']);
//   }

//   onFileSelected(event: any) {
//     const file = event.files?.[0];
//     if (file) {
//       this.productForm.get('image')?.setValue(file);
//     }
//   const reader = new FileReader();
//     reader.onload = () => {
//     this.imagePreview = reader.result;
//     };
//   reader.readAsDataURL(file);
//   }


// }
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

    if (query) {
      searchs.push(`name=ilike=${query}%`);
    }

    const idsIngredient = (this.productItemIngredientForm.get('ingredients') as FormArray)
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
  protected editIngredient(index: number): void {
  const ingredientCtrl = (this.productForm.get('ingredients') as FormArray).at(index);
  if (!ingredientCtrl) return;
  this.productItemIngredientForm.patchValue(ingredientCtrl.value);
  this.currentIndex = index;
}

protected editAdditional(index: number): void {
  const additionalCtrl = (this.productForm.get('additional') as FormArray).at(index);
  if (!additionalCtrl) return;
  this.additionalForm.patchValue(additionalCtrl.value);
  this.currentIndex = index;
}

protected editProductVariable(index: number): void {
  const pvCtrl = (this.productForm.get('productVariables') as FormArray).at(index);
  if (!pvCtrl) return;
  this.productVariableForm.patchValue(pvCtrl.value);
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
