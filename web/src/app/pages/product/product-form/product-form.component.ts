import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AutocompleteComponent, CancelButtonComponent, DatePickerComponent, GenericButtonComponent, ImageUploadComponent, InputComponent, SaveButtonComponent, ToggleComponent } from "cardappio-component-hub";
import { Observable } from 'rxjs';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'app-product-form',
  imports: [ ReactiveFormsModule, DatePickerComponent, InputComponent, ToggleComponent, CancelButtonComponent, GenericButtonComponent, ImageUploadComponent, SaveButtonComponent, AutocompleteComponent],
  providers: [ProductService],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.scss'
})
export class ProductFormComponent implements OnInit {
  
  form: FormGroup<any> = new FormGroup({});

  constructor(
    private readonly builder: FormBuilder,
    private service: ProductService,
    // private categoryService: CategoryService,
    private router: Router,
    private route: ActivatedRoute
  ) { }
  
  ngOnInit(): void {
    this.initForm();
    this.checkRoute();
  };

  private initForm(): void {
    this.form = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      quantity: ['', [Validators.required, Validators.min(0)]],
      description: [''],
      active: [true],
      category: [null],
      expirationDate: [''],
      image: [''],
      note: ['']
    })
  }
  
  private checkRoute(): void {
    const { id } = this.route.snapshot.params;

    if (id != 'new') {
      this.loadProduct(id);
    }    
  }

  private loadProduct(id: string) {
    this.service.findById(id).subscribe(product => {
      this.form.patchValue(product)
    });
  }

  create() {
    const { id } = this.route.snapshot.params;

    if (this.form.invalid) {
      return;
    }
      

    if (id != 'new') {
      this.service.update(id, this.form.value);
    } else {
      this.service.create(this.form.value).subscribe(() => this.router.navigate(['product']));
    }
  }

  cancel() {
    this.router.navigate(['product']);
  }

  categories = (query: string): Observable<any[]> => {
    return this.service.findAll(20, query);
  }
}
