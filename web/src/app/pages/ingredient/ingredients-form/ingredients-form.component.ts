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
      quantity: [null, Validators.required],
      active: [true],
      unityOfMeasurement: ['Litro'],
      expirationDate: [''],
      allergenic: [false]
    });
  }

  
  private checkRoute() {
    const { id } = this.route.snapshot.params;

    if (id != 'new') {
      this.loadIngredient(id);
    }
  }

  private loadIngredient(id: string) {
    this.service.findById(id).subscribe(ingredient => {  
      this.form.patchValue({...ingredient, expirationDate: new Date(ingredient.expirationDate)});      
    })
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
