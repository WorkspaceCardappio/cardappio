import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AutocompleteComponent, CancelButtonComponent, ImageUploadComponent, InputComponent, SaveButtonComponent, ToggleComponent } from 'cardappio-component-hub';
import { Observable } from 'rxjs';
import { CategoryService } from '../service/category.service';

@Component({
  selector: 'app-category-form',
  imports: [InputComponent,
    ReactiveFormsModule,
    ImageUploadComponent,
    CommonModule,
    ToggleComponent,
    CancelButtonComponent,
    SaveButtonComponent,
    AutocompleteComponent],
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
    const { id } = this.route.snapshot.params;
    if (id != 'new') {
      this.loadCategory(id);
    }
  }

  private loadCategory(id: string) {
    this.service.findById(id).subscribe(category => {
      this.form.patchValue(category)
    })
  }

  create() {
    if (this.form.invalid)
      return;
    
    if (this.getActiveId != 'new') {
      this.service.update(id, this.form.value).subscribe(() => this.router.navigate(['category']));
    } else {
      this.service.create(this.form.value).subscribe(() => this.router.navigate(['category']));
    }  
  }

  cancel() {
    this.router.navigate(['category']);
  }

  categories = (query: string): Observable<any[]> => {
    return this.service.findAll(20, query);
  }
}
