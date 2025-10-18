import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { DatePickerModule } from 'primeng/datepicker';
import { FieldsetModule } from 'primeng/fieldset';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { map, Observable } from 'rxjs';
import { Ingredient } from '../../../model/ingredient';
import { IngredientStock } from '../../../model/ingredient-stock';
import { IngredientService } from '../service/ingredient.service';


@Component({
  selector: 'app-ingredients-form',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    InputTextModule,
    ButtonModule,
    ToggleSwitchModule,
    BreadcrumbModule,
    FieldsetModule,
    SelectModule,
    DatePickerModule,
    InputNumberModule
  ],
  providers: [IngredientService],
  templateUrl: './ingredients-form.component.html',
  styleUrl: './ingredients-form.component.scss'
})
export class IngredientsFormComponent implements OnInit {
  
  home = { icon: 'pi pi-home', routerLink: '/home' };
  items = [
    { label: 'Ingredientes', routerLink: '/ingredient' },
    { label: 'Novo', routerLink: '/ingredient/new' },
  ];

  ingredient!: Ingredient | null;
  stocks: IngredientStock[] = [];

  form: FormGroup<any> = new FormGroup({});

  filteredIngredients: any[] = [];
  loading: boolean = false;

  unityOfMeasurement: any[] = [];
  selectedUnity: any | undefined;
  unitItems: any[] = [];

  constructor(
    private readonly builder: FormBuilder,
    private service: IngredientService,
    private router: Router,
    private route: ActivatedRoute,
  ){}
  
  ngOnInit(): void {
    this.initForm();
    this.checkRoute();
     this.unityOfMeasurement = [
      { code: 1, description: 'Litro' },
      { code: 2, description: 'Mililitro' },
      { code: 3, description: 'Grama' },
      { code: 4, description: 'Quilograma' },
      { code: 5, description: 'Unidade' },
    ]
  }


  private initForm() {
    this.form = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      active: [true, Validators.required],
      quantity: [0, Validators.required],
      unityOfMeasurement: ['', Validators.required],
      allergenic: [false, Validators.required],
      stocks: this.builder.array([])
    });
  }

  
  private checkRoute() {
    const { id } = this.route.snapshot.params;

    if (id != 'new') {
      this.loadIngredient(id);
    }
  }

  private loadIngredient(id: string): void {
    this.service.findById(id)
      .subscribe({
        next: (ingredient: Ingredient) => {
          this.ingredient = ingredient;
          this.form.patchValue(ingredient);
          this.stocks = ingredient.stocks || [];
        },
        error: (err) => console.error('Erro ao carregar ingrediente', err),
      });
  }

  create() {
    if (this.form.invalid)
      return;

    const { id } = this.route.snapshot.params;
    if (id != 'new') {
      this.service.update(id, this.form.value).subscribe(() => this.router.navigate(['ingredient']))
    } else {
      this.service.create(this.form.value).subscribe(() => this.router.navigate(['ingredient']))
    }
  }

  cancel() {
    this.router.navigate(['ingredient']);
  }

  unitOfMeasurement = (query: string): Observable<any[]> => {
  return this.service.getUnitOfMeasurement().pipe(
    map(units => {
      const filtered = query
        ? units.filter(u => u.description.toLowerCase().includes(query.toLowerCase()))
        : units;

      return filtered;
    })
  );
  }

  getUnitDescription = (item: any): string => {
    return `${item.code} - ${item.description}`;
  }

  selectedDate(data: any) {
    this.form.get('expirationDate')?.setValue(data);
  }
}
