import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ImageUploadComponent, InputComponent, ToggleComponent, CancelButtonComponent, SaveButtonComponent } from 'cardappio-component-hub';
import { CategoryService } from '../service/category.service';
import { Category } from '../model/category';
import { Router } from '@angular/router';

@Component({
  selector: 'app-category-form',
  imports: [InputComponent,
    ReactiveFormsModule,
    ImageUploadComponent,
    CommonModule,
    ToggleComponent,
    CancelButtonComponent,
    SaveButtonComponent],
  providers: [CategoryService],
  templateUrl: './category-form.component.html',
  styleUrl: './category-form.component.scss'
})
export class CategoryFormComponent implements OnInit {

  form: FormGroup<any> = new FormGroup({}) ;

  constructor(
    private readonly builder: FormBuilder,
    private service: CategoryService,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.form = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      active: [true],
      image: [''],
      parent: [],
    });
  }

  create() {
    console.log(this.form.invalid)
    if (this.form.invalid)
      return;
    this.service.create(this.form.value)
        .subscribe(() => this.router.navigate(['category']));    
  }

  cancel() {
    this.router.navigate(['category'])
  }

  input(name: string) {
    this.form.get('name')?.setValue(name);
  }
}
