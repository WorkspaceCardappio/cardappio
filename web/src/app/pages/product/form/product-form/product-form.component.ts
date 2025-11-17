import { CommonModule } from '@angular/common';
import {
  ChangeDetectorRef,
  Component,
  OnInit,
  signal,
  WritableSignal,
} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { DatePickerModule } from 'primeng/datepicker';
import { FieldsetModule } from 'primeng/fieldset';
import { FileUploadModule } from 'primeng/fileupload';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { StepperModule } from 'primeng/stepper';
import { TableModule } from 'primeng/table';
import { TextareaModule } from 'primeng/textarea';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { Loader } from '../../../../model/loader';
import { CategoryService } from '../../../category/service/category.service';
import { OrderGroupId } from '../../../order/model/order-group-id.model';
import { ProductService } from '../../service/product.service';
import { ProductAdditionalComponent } from '../product-additional/product-additional.component';
import { ProductIngredientComponent } from '../product-ingredient/product-ingredient.component';
import { ProductProductItemComponent } from '../product-product-item/product-product-item.component';
import { ProductVariableComponent } from '../product-variable/product-variable.component';

@Component({
  selector: 'app-product-form',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    BreadcrumbModule,
    FieldsetModule,
    InputTextModule,
    InputNumberModule,
    DatePickerModule,
    ToggleSwitchModule,
    AutoCompleteModule,
    FileUploadModule,
    TextareaModule,
    StepperModule,
    ButtonModule,
    TableModule,
    SelectModule,
    ProductIngredientComponent,
    ProductProductItemComponent,
    FloatLabelModule,
    ProductAdditionalComponent,
    ProductVariableComponent,
  ],
  providers: [CategoryService, ProductService],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.scss',
})
export class ProductFormComponent implements OnInit {
  stepIndex = 1;

  id: string = '';
  isEdit = false;

  productForm: FormGroup<any> = new FormGroup({});

  items: MenuItem[] = [];
  home: MenuItem = {};

  productGroupId: WritableSignal<OrderGroupId> = signal({
    order: null,
    product: null,
    item: null,
  });

  filteredCategories: any[] = [];
  categories: Loader = { values: [] };
  loading: boolean = false;
  currentIndex: number | null = null;

  imagePreview: string | ArrayBuffer | null = null;

  constructor(
    private readonly builder: FormBuilder,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly categoryService: CategoryService,
    private readonly productService: ProductService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.productForm = this.initForm();
    this.initBreadcrumb();

    this.id = this.route.snapshot.paramMap.get('id') || '';
    this.isEdit = this.id != 'new';

    if (this.isEdit) {
      this.loadProduct(this.id);
    }
  }

  private loadProduct(id: string) {
    this.productService.findById(id).subscribe((product) => {
      this.productForm.patchValue(product);
      if (product.imageUrl) {
        this.imagePreview = product.imageUrl;
      }
    });
  }

  private initBreadcrumb(): void {
    this.items = [
      { label: 'Produtos', routerLink: '/product' },
      { label: 'Novo Produto' },
    ];
    this.home = { icon: 'pi pi-home', routerLink: '/' };
  }

  private initForm() {
    const form = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      price: [1],
      quantity: [1],
      description: [''],
      expirationDate: [null],
      active: [true],
      image: [null],
      note: [''],
      category: [null, Validators.required],
    });

    return form;
  }

  searchCategories(event: any) {
    const query = event.query || '';
    const searches = query ? [`name=ilike=${query}%`] : [];
    const completeParams = `pageSize=20&search=${searches.join(';')}`;

    this.categoryService.findAllDTO(completeParams).subscribe({
      next: (page) => (this.filteredCategories = page.content),
      error: () => (this.categories.values = []),
      complete: () => this.cdr.markForCheck(),
    });
  }

  onCancel(): void {
    this.navigateToList();
  }

  onSave(activateCallback: () => void): void {
    if (this.productForm.invalid) return;

    const payload = this.productForm.getRawValue();
    this.saveDraftProduct();
    if (this.isEdit) {
      this.updateProduct(payload, activateCallback);
      return;
    }
    this.createProduct(payload, activateCallback);
  }

  saveDraftProduct() {
    const draft = this.productForm.getRawValue();
    localStorage.setItem('product_draft', JSON.stringify(draft));
  }

  private createProduct(payload: any, activateCallback: () => void): void {
    const file = payload.image;
    const dto = {
      ...payload,
      category: payload.category?.id || payload.category,
      image: null
    };

    this.productService.saveWithImage(dto, file).subscribe({
      next: (response: any) => {
        this.next(activateCallback);
        this.setProductId(response.id);
      },
      error: (error: any) => console.error('Erro ao criar produto: ', error),
    });
  }

  private updateProduct(payload: any, activateCallback: () => void): void {
    const file = payload.image;
    const dto = {
      ...payload,
      category: payload.category?.id || payload.category,
      image: null
    };

    this.productService.updateWithImage(this.id, dto, file).subscribe({
      next: () => this.next(activateCallback),
      error: (error: any) =>
        console.error('Erro ao atualizar produto: ', error),
    });
  }

  protected navigateToList(): void {
    this.router.navigate(['/product']);
  }

  prev(activateCallback: () => void) {
    this.stepIndex--;
    activateCallback();
  }

  next(activateCallback: () => void) {
    this.stepIndex++;
    activateCallback();
  }

  onFileSelected(event: any) {
    const file = event.files?.[0];
    if (file) this.productForm.get('image')?.setValue(file);
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    };
    reader.readAsDataURL(file);
  }

  private setProductId(id: string) {
    this.productGroupId.set({
      ...this.productGroupId(),
      product: id,
    });
  }

  finalizeProduct() {
    this.productService
      .finalize(this.productGroupId().product!)
      .subscribe(() => this.router.navigate(['product']));
  }
}
