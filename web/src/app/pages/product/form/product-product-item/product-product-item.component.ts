import { CommonModule } from '@angular/common';
import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { Loader } from '../../../../model/loader';
import { ProductService } from '../../service/product.service';
import { ProductIngredientService } from '../product-ingredient/service/product-ingredient.service';
import { ProductItemService } from './service/product-item.service';

@Component({
  selector: 'app-product-product-item',
  imports: [
    SelectModule,
    InputNumberModule,
    ToggleSwitchModule,
    TableModule,
    ButtonModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
  ],
  templateUrl: './product-product-item.component.html',
  styleUrl: './product-product-item.component.scss',
})
export class ProductProductItemComponent implements OnInit {
  @Input({ required: true }) productId?: string | null;
  @Output() prevEmitter: EventEmitter<void> = new EventEmitter();
  @Output() nextEmitter: EventEmitter<void> = new EventEmitter();

  form: FormGroup = this.builder.group({});
  itemSize: any[] = [];
  products: Loader = { values: [] };
  filteredProducts: any[] = [];

  loading: boolean = false;

  home = { icon: 'pi pi-home', routerLink: '/home' };

  productsToSave: any[] = [];
  indexToEditProduct: number | null = null;

  items = [
    { label: 'Produto', routerLink: '/product' },
    { label: 'Novo', routerLink: '/product/new' },
  ];

  constructor(
    private readonly builder: FormBuilder,
    private readonly productIngredientService: ProductIngredientService,
    private readonly productService: ProductService,
    private readonly productItemService: ProductItemService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.form = this.createProductItemForm();

    this.itemSize = [
      { code: 1, description: 'Único' },
      { code: 2, description: 'Pequeno' },
      { code: 3, description: 'Médio' },
      { code: 4, description: 'Grande' },
    ];

    this.getIngredients();
  }

  createProductItemForm(): FormGroup {
    const form = this.builder.group({
      id: [''],
      product: [this.productId],
      quantity: [
        1,
        Validators.compose([Validators.required, Validators.min(1)]),
      ],
      size: [null],
      description: [''],
      price: [
        0.01,
        Validators.compose([Validators.required, Validators.min(0)]),
      ],
      active: [true],
      ingredients: this.builder.array([]),
    });
    return form;
  }

  public getIngredients(): void {
    if (!this.productId) {
      return;
    }

    this.productIngredientService
      .getProductIngredient(this.productId)
      .subscribe((ingredients: any) => {
        const formArray = this.getProductItemIngredientsFormArray();
        formArray.clear();
        (ingredients as any[]).forEach((ingredient) => {
          formArray.push(this.createIngredientFormGroup(ingredient));
        });
        this.cdr.markForCheck();
      });
  }

  public initProductItemIngredientFormArray(ingredients: any[]): void {
    const productItemIngredientsFormArray = this.form.get(
      'ingredients'
    ) as FormArray;

    ingredients.forEach((ingredient) => {
      productItemIngredientsFormArray.push(
        this.createIngredientFormGroup(ingredient)
      );
    });
  }

  private createIngredientFormGroup(ingredient?: any): FormGroup {
    return this.builder.group({
      id: [''],
      productItem: [this.form.get('id')?.value],
      ingredient: [ingredient],
      quantity: [
        ingredient?.quantity || 0,
        Validators.compose([Validators.required, Validators.min(0)]),
      ],
    });
  }



  getProductItemIngredientsFormArray(): FormArray {
    return this.form.get('ingredients') as FormArray;
  }

  getIngredientFormGroup(index: number): FormGroup {
    return this.getProductItemIngredientsFormArray().at(index) as FormGroup;
  }

  onAddProductsToSave(): void {
    const productItemData = this.form.getRawValue();
    this.productItemService.createProductItem(productItemData);

    if (this.indexToEditProduct != null) {
      this.productsToSave[this.indexToEditProduct] = productItemData;
      this.indexToEditProduct = null;
      return;
    }

    this.productsToSave.push(productItemData);
  }

  onEditProduct(index: number) {
    if (index < 0) return;

    const product = this.productsToSave[index];

    if (product) {
      this.indexToEditProduct = index;

      const array = this.getProductItemIngredientsFormArray();
      array.clear();

      product.ingredients
        .forEach((ingredient: any) => {

          const ingredientForm = this.createIngredientFormGroup();
          ingredientForm.patchValue(ingredient);
          array.push(ingredientForm);
          console.log(this.form);
        });

      this.form.patchValue(product);
    }

  }

  onDeleteProduct(index: number) {
    if (index < 0) return;
    this.productsToSave.splice(index, 1);
  }

  searchProducts(event: any): void {
    const query = event.query || '';
    const searches = query ? [`name=ilike=${query}%`] : [];
    const completeParams = `pageSize=20&search=${searches.join(';')}`;

    this.productService.findAllDTO(completeParams).subscribe({
      next: (page) => (this.products.values = page.content),
      error: () => (this.products.values = []),
      complete: () => this.cdr.markForCheck(),
    });
  }
}
