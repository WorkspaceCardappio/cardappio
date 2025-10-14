import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { map, Observable } from 'rxjs';
import { IngredientService } from '../service/ingredient.service';

@Component({
  selector: 'app-ingredients-form',
  imports: [
   
],
  templateUrl: './ingredients-form.component.html',
  styleUrl: './ingredients-form.component.scss'
})
export class IngredientsFormComponent implements OnInit {
  form: FormGroup<any> = new FormGroup({});
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
  }


  private initForm() {
    this.form = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      quantity: ['', [Validators.required, Validators.min(0)]],
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
      console.log(ingredient);
      
      this.form.patchValue(ingredient);
      console.log(this.form);
      
    })
  }

  create() {
    if (this.form.invalid)
      return;

    const { id } = this.route.snapshot.params;
    console.log(this.form.value)
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
