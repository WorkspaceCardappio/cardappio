import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ImageUploadComponent, InputComponent, ToggleComponent, CancelButtonComponent, SaveButtonComponent } from 'cardappio-component-hub';

@Component({
  selector: 'app-category-form',
  imports: [InputComponent,
    ReactiveFormsModule,
    ImageUploadComponent,
    CommonModule,
    ToggleComponent,
    CancelButtonComponent,
    SaveButtonComponent],
  
  templateUrl: './category-form.component.html',
  styleUrl: './category-form.component.scss'
})
export class CategoryFormComponent implements OnInit {

  form: FormGroup<any> = new FormGroup({});

  constructor(
    private readonly builder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.form = this.builder.group({
      name: ['', Validators.required],
      active: [true],
      image: [''],
      parent: ['']
    });
  }
}
