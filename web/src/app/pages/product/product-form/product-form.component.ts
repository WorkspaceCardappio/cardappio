import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
// import { AdditionalComponent } from '../../additional/additional-modal/additional-modal.component';
import { MessageService } from 'primeng/api';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
import { CategoryService } from '../../category/service/category.service';
import { ProductService } from '../service/product.service';


@Component({
  selector: 'app-product-form',
    imports: [
        InputTextModule,
        InputNumberModule,
        MessageModule,
        ReactiveFormsModule,
        IconFieldModule,
        InputIconModule,
        CommonModule],
    providers: [
        ProductService,
        MessageService
    ],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.scss'
})
export class ProductFormComponent implements OnInit {
    messageService = inject(MessageService);
    form: FormGroup<any> = new FormGroup({});
    formSubmitted = false;
//   @ViewChild(AdditionalComponent)
//   additionalToggle!: AdditionalComponent;
//   isExpanded: boolean = false;

//   togglePanel(): void {
//     this.isExpanded = !this.isExpanded;
//  }
  
  constructor(
    private readonly builder: FormBuilder,
    private service: ProductService,
    private categoryService: CategoryService,
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
      price: [null, [Validators.required, Validators.min(0)]],
      quantity: [null, [Validators.required, Validators.min(0)]],
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

//   create() {
//     const { id } = this.route.snapshot.params;

//     if (this.form.invalid) {
//       return;
//     }
      

//     if (id != 'new') {
//       this.service.update(id, this.form.value);
//     } else {
//       this.service.create(this.form.value).subscribe(() => this.router.navigate(['product']));
//     }
    //   }

    onSubmit() {
        this.formSubmitted = true;
        if (this.form.valid) {
            this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Form Submitted', life: 3000 });
            this.form.reset();
            this.formSubmitted = false;
        }
    }

    isInvalid(controlName: string) {
        const control = this.form.get(controlName);
        return control?.invalid && (control.touched || this.formSubmitted);
    }

  selectedDate(data: any) {
    this.form.get('expirationDate')?.setValue(data);
  }

  cancel() {
    this.router.navigate(['product']);
  }

  redirectToCategory() {
    this.router.navigate(['category'])
  }

  categories = (query: string): Observable<any[]> => {

    const searchs = [];

    if (query) {
      searchs.push(`name=ilike=${query}%`);
    }

    return this.categoryService.findAll(20, searchs.join(';'));
  }

  getCategory = (item: any): string => {
    return `${item.name}`
  }
}
