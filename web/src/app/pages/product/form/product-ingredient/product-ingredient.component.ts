import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { IngredientService } from '../../../ingredient/service/ingredient.service';
import { ProductService } from '../../service/product.service';
import { ProductIngredientService } from './service/product-ingredient.service';

@Component({
  selector: 'app-product-ingredient',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    ButtonModule,
    AutoCompleteModule,
    TableModule
  ],
  providers: [
    IngredientService,
    ProductService
  ],
  templateUrl: './product-ingredient.component.html',
  styleUrl: './product-ingredient.component.scss'
})
export class ProductIngredientComponent implements OnInit {
  
  @Input({ required: true }) productId?: string | null;
  @Output() prevEmitter: EventEmitter<void> = new EventEmitter();
  @Output() nextEmitter: EventEmitter<void> = new EventEmitter();

  form: FormArray<FormGroup<any>> = new FormArray<FormGroup<any>>([])

  filteredIngredients: any[] = [];
  selectedIngredient: any = null;
  ingredients: any[] = [];

  loading: boolean = false;

  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [
    { label: 'Produto', routerLink: '/product' },
    { label: 'Novo', routerLink: '/product/new' },
  ];

  constructor(
    private readonly ingredientService: IngredientService,
    private readonly productIngredientService: ProductIngredientService,
    private readonly builder: FormBuilder,
    private cdr: ChangeDetectorRef,
  ){}

  ngOnInit(): void {}

  searchIngredients(event: any) {
    const query = event.query;
    
    let search = '&search=active==TRUE';
    search += query ? `;name=ilike=${query}` : '';

    const completeParams = `pageSize=20;${search}`
    
    this.ingredientService.findAllDTO(completeParams).subscribe({
      next: (data) => { this.filteredIngredients = data.content; },
      error: (err) => { console.error('Erro ao buscar ingredientes', err); },
      complete: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  createIngredientForm(ingredient: any): FormGroup {
    const form = this.builder.group({
      id: [''],
      product: [this.productId],
      ingredient: this.builder.group(ingredient),
    });

    return form;
  }

  onSelectionChange(event: any) {
    this.selectedIngredient = event.value;
  }

  protected addIngredient() {
    if (!this.selectedIngredient) return;

    const exists = this.form.value.some((i: any) => i.id === this.selectedIngredient.id);
    if (exists) return;

    const formGroup = this.createIngredientForm(this.selectedIngredient);
    this.form.push(formGroup);

    this.selectedIngredient = null;
    this.saveLocalStorage();
  }

  protected deleteIngredient(index: number) {
    this.form.removeAt(index);
    this.saveLocalStorage();
  }

  private saveLocalStorage() {
    const ingredients = this.form.getRawValue();
    const draft = this.getDraftProduct();
    draft.ingredients = ingredients;
    localStorage.setItem('product_ingredient', JSON.stringify(draft));
  }


  private getDraftProduct(): any {
    const draft = localStorage.getItem('product_ingredient');
    return draft ? JSON.parse(draft) : { id: this.productId, ingredients: [] };
  }

  onSave() {
    if (!this.form.value.length) {
      this.nextEmitter.emit();
      return;
    }
    this.saveLocalStorage();
    this.productIngredientService.createProductIngredient(this.form.getRawValue())
      .subscribe(() => this.nextEmitter.emit());
    
  }

  prev() {
    this.prevEmitter.emit();
  }
}
