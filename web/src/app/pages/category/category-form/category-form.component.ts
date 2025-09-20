import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ImageUploadComponent, InputComponent, ToggleComponent, CancelButtonComponent, SaveButtonComponent } from 'cardappio-component-hub';
import { CategoryService } from '../service/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UUID } from 'node:crypto';

@Component({
  selector: 'app-category-form',
  imports: [InputComponent,
    ReactiveFormsModule,
    ImageUploadComponent,
    CommonModule,
    ToggleComponent,
    CancelButtonComponent,
    SaveButtonComponent,
  ],
  providers: [CategoryService],
  templateUrl: './category-form.component.html',
  styleUrl: './category-form.component.scss'
})
export class CategoryFormComponent implements OnInit {

  form: FormGroup<any> = new FormGroup({}) ;

  constructor(
    private readonly builder: FormBuilder,
    private service: CategoryService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.checkRoute();
    };
  
  private initForm() {
    this.form = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      active: [true],
      image: [''],
      parent: [],
    });
  }

  private checkRoute() {
    const path = this.route.snapshot.routeConfig?.path;

    if (path == 'new')
      return;

    const id = this.route.snapshot.paramMap.get('id') as UUID;
    if (id) 
      this.loadCategory(id);
  }

  private loadCategory(id: UUID) {
    this.service.findById(id).subscribe(category => {
      this.form.patchValue(category)
    })
  }

  create() {
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
