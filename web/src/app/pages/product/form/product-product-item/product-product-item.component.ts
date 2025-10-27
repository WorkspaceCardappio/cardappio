import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { Loader } from '../../../../model/loader';
import { ProductService } from '../../service/product.service';
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
    CommonModule
  ],
  templateUrl: './product-product-item.component.html',
  styleUrl: './product-product-item.component.scss'
})
export class ProductProductItemComponent implements OnInit {
  @Input({ required: true }) ingredientId!: string;
  @Output() nextEmitter: EventEmitter<void> = new EventEmitter();
  @Output() onProductSelect: EventEmitter<void> = new EventEmitter();

  productItemForm: FormGroup = new FormGroup({});

  itemSize: any[] = [];
  products: Loader = { values: []}
  filteredProducts: any[] = [];

  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [
    { label: 'Produto', routerLink: '/product' },
    { label: 'Novo', routerLink: '/product/new' },
  ];

  constructor(
    private readonly builder: FormBuilder,
    private readonly service: ProductItemService,
    private readonly productService: ProductService,
    private cdr: ChangeDetectorRef
  ) { }
  
  ngOnInit(): void {
    this.itemSize = [
      { code: 1, description: 'Único' },
      { code: 2, description: 'Pequeno' },
      { code: 3, description: 'Médio' },
      { code: 4, description: 'Grande' },
    ];
    this.productItemForm = this.initForm();
    this.loadFromStorage();
  }

  private initForm(product: any = null, data: any = null) {
    const productItemForm = this.builder.group({
      id: [data.id || ''],
      product: [data.product || null, Validators.required],
      quantity: [data?.quantity || 0],
      size: [data?.size || null, Validators.required],
      description: [data.description || ''],
      price: [data?.price || 0, Validators.required],
      active: [data?.active || true],
      ingredients: this.builder.array([])
    })
  
    productItemForm.get('product')?.valueChanges.subscribe((product: any) => {
      if (!product?.id) return;

      this.onProductSelect.emit(product?.id);
    });

    const ingredients = productItemForm.get('ingredients') as FormArray;
    const ingredientsSource = data?.ingredients || product?.ingredients;

    ingredientsSource.forEach((ingredient: any) => {
      ingredients.push(this.builder.group({
        id: [ingredient.id || ''],
        productItem: [productItemForm.get('id')],
        ingredient: [ingredient],
        quantity: [data?.quantity || 0, Validators.required],
        unityOfMeasurement: [ingredient.unityOfMeasurement],
        active: [data?.active || ingredient?.active || true, Validators.required],
        allergenic: [data?.allergenic || ingredient?.allergenic || false, Validators.required]
      }));

      productItemForm.valueChanges.subscribe(() => this.saveLocalStorage());
      ingredients.valueChanges.subscribe(() => this.saveLocalStorage());
    })
    return productItemForm;
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
  
  saveLocalStorage() {
    const data = this.productItemForm.getRawValue();
    localStorage.setItem('product_items_draft', JSON.stringify(data));
  }


  loadFromStorage() {
    const draft = localStorage.getItem('product_items_draft');
    if (draft) {
      const items = JSON.parse(draft);
      items.forEach((item: any) => {
        const form = this.initForm(item.product);
        form.patchValue({
          size: item.size,
          price: item.price,
          quantity: item.quantity,
          description: item.description,
          active: item.active
        });

        const ingredientsArray = form.get('ingredients') as FormArray;
        item.ingredients?.forEach((ing: any) => {
          ingredientsArray.push(this.builder.group({
            id: [ing.id],
            name: [ing.name],
            quantity: [ing.quantity],
            unityOfMeasurement: [ing.unityOfMeasurement]
          }));
        });
      });
    }
  }

  onSave() {
    this.service.create(this.productItemForm.getRawValue())
      .subscribe((item: any) => this.nextEmitter.emit());
  }
}