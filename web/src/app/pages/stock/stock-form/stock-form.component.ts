import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { DatePickerModule } from 'primeng/datepicker';
import { FieldsetModule } from 'primeng/fieldset';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { Stock } from '../../../model/stock';
import { StockService } from '../service/stock.service';
import { IngredientService } from '../../ingredient/service/ingredient.service';
import { Ingredient } from '../../../model/ingredient';

@Component({
  selector: 'app-stock-form',
  standalone: true,
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
  providers: [StockService, IngredientService],
  templateUrl: './stock-form.component.html',
  styleUrl: './stock-form.component.scss'
})
export class StockFormComponent implements OnInit {

  home = { icon: 'pi pi-home', routerLink: '/home' };
  items = [
    { label: 'Estoques', routerLink: '/stock' },
    { label: 'Novo', routerLink: '/stock/new' },
  ];

  stock!: Stock | null;
  ingredients: Ingredient[] = [];
  form: FormGroup<any> = new FormGroup({});
  loading: boolean = false;

  constructor(
    private readonly builder: FormBuilder,
    private service: StockService,
    private ingredientService: IngredientService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.loadIngredients();
    this.checkRoute();
  }

  private initForm() {
    this.form = this.builder.group({
      id: [''],
      ingredient: [null, Validators.required], // ← substitui o "name"
      quantity: [0, [Validators.required, Validators.min(0)]],
      number: ['', Validators.required],
      expirationDate: [null, Validators.required],
      deliveryDate: [null, Validators.required]
    });
  }

  private checkRoute() {
    const { id } = this.route.snapshot.params;

    if (id != 'new') {
      this.loadStock(id);
    }
  }

  private loadStock(id: string): void {
    this.service.findById(id)
      .subscribe({
        next: (stock: Stock) => {
          this.stock = stock;
          this.form.patchValue({
            ...stock,
            expirationDate: stock.expirationDate ? new Date(stock.expirationDate) : null,
            deliveryDate: stock.deliveryDate ? new Date(stock.deliveryDate) : null
          });
        },
        error: (err) => console.error('Erro ao carregar estoque', err),
      });
  }

  private loadIngredients(): void {
    this.ingredientService.findAll()
      .subscribe({
        next: (ingredients: Ingredient[]) => {
          this.ingredients = ingredients;
        },
        error: (err) => console.error('Erro ao carregar ingredientes', err),
      });
  }

  create() {
    if (this.form.invalid)
      return;

    const formValue = this.form.value;

    const payload = {
      ...formValue,
      ingredient: { id: formValue.ingredient }
    };

    const { id } = this.route.snapshot.params;

    if (id != 'new') {
      this.service.update(id, payload)
        .subscribe({
          next: () => this.router.navigate(['stock']),
          error: (err) => console.error('Erro ao atualizar estoque', err)
        });
    } else {
      this.service.create(payload)
        .subscribe({
          next: () => this.router.navigate(['stock']),
          error: (err) => console.error('Erro ao criar estoque', err)
        });
    }
  }

  cancel() {
    this.router.navigate(['stock']);
  }
}
