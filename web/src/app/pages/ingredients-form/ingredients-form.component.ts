import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AutocompleteComponent, CancelButtonComponent, InputComponent, SaveButtonComponent, ToggleComponent } from "cardappio-component-hub";
import { Observable } from 'rxjs';
import { IngredientService } from '../../service/ingredient.service';

@Component({
  selector: 'app-ingredients-form',
  imports: [
    InputComponent,
    ReactiveFormsModule,
    ToggleComponent,
    CancelButtonComponent,
    SaveButtonComponent,
    AutocompleteComponent
],
  templateUrl: './ingredients-form.component.html',
  styleUrl: './ingredients-form.component.scss'
})
export class IngredientsFormComponent implements OnInit {
  form: FormGroup<any> = new FormGroup({});

  constructor(
    private readonly builder: FormBuilder,
    private service: IngredientService,
    private router: Router,
    private route: ActivatedRoute
  ){}
  
  ngOnInit(): void {
    this.initForm();
    this.checkRoute();
  }

  private initForm() {
    this.form = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      quantity: ['', [Validators.required, Validators.min(0)]],
      active: [true],
      unityOfMeasurement: ['', Validators.required],
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
      this.form.patchValue(ingredient);
    })
  }

  unitOfMeasurement = (query: string): Observable<any[]> => {
    return this.service.findAll(20, query);
  }

}
