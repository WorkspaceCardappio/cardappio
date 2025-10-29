import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { IngredientService } from '../../../ingredient/service/ingredient.service';
import { ProductService } from '../../service/product.service';

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
  @Input({ required: true }) productId!: string | null;
  @Output() prevEmitter: EventEmitter<void> = new EventEmitter();
  @Output() nextEmitter: EventEmitter<void> = new EventEmitter();

  form: FormArray<FormGroup<any>> = new FormArray<FormGroup<any>>([])

  filteredIngredients: any[] = [];
  selectedIngredient: any = null;

  loading: boolean = false;

  constructor(
    private readonly ingredientService: IngredientService,
    private readonly builder: FormBuilder,
    private cdr: ChangeDetectorRef,
  ){}

  ngOnInit(): void {
    this.loadFromStorage();
  }

  searchIngredients(event: any) {
    const query = event.query;
    const searchs = [];
    if (query) searchs.push(`name=ilike=${query}%`);
    this.ingredientService.findAll(20, searchs.join(';')).subscribe({
      next: (data) => { this.filteredIngredients = data; },
      error: (err) => { console.error('Erro ao buscar ingredients', err); },
      complete: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  createIngredientForm(ingredient: any): FormGroup {
    const form = this.builder.group({
      id: [ingredient.id],
      name: [ingredient.name, Validators.required],
      quantity: [ingredient.quantity, Validators.required],
      unityOfMeasurement: [ingredient.unityOfMeasurement],
      active: [ingredient.active ?? true],
      allergenic: [ingredient.allergenic ?? false],
      stocks: [ingredient.stocks || []],
    });
    return form;
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
    localStorage.setItem('product_draft', JSON.stringify(draft));
  }

  private loadFromStorage() {
    const draft = this.getDraftProduct();

    if (draft.ingredients?.length) {
      draft.ingredients.forEach((ingredient: any) => {
        this.form.push(this.createIngredientForm(ingredient));
      });
    }
  }

  private getDraftProduct(): any {
    const draft = localStorage.getItem('product_draft');
    return draft ? JSON.parse(draft) : { id: this.productId, ingredients: [] };
  }

  next() {
    this.saveLocalStorage();
    this.nextEmitter.emit();
  }

  prev() {
    this.prevEmitter.emit();
  }
}
