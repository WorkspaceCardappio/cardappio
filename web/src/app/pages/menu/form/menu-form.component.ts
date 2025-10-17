import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { FieldsetModule } from 'primeng/fieldset';
import { FileUploadModule } from 'primeng/fileupload';
import { FloatLabelModule } from 'primeng/floatlabel';
import { IconFieldModule } from 'primeng/iconfield';
import { InputNumber } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { StepperModule } from 'primeng/stepper';
import { TableModule } from 'primeng/table';
import { TextareaModule } from 'primeng/textarea';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { ProductService } from '../../product/product.service';
import { MenuService } from '../service/menu.service';

@Component({
  selector: 'menu-form',
  imports: [
  ReactiveFormsModule,
    CommonModule,
    InputTextModule,
    AutoCompleteModule,
    FileUploadModule,
    ButtonModule,
    ToggleSwitchModule,
    BreadcrumbModule,
    IconFieldModule,
    CardModule,
    FieldsetModule,
    TextareaModule,
    FloatLabelModule,
    StepperModule,
    InputNumber,
    TableModule,
  ],
  providers: [MenuService, ProductService],
  templateUrl: './menu-form.component.html',
  styleUrl: './menu-form.component.scss',
})
export class MenuFormComponent implements OnInit {
  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [
    { label: 'Cardápio', routerLink: '/menu' },
    { label: 'Novo', routerLink: '/menu/new' },
  ];

  form: FormGroup<any> = new FormGroup({});
  productForm: FormGroup<any> = new FormGroup({});
  isEdit = false;

  filteredProducts: any[] = [];
  loading = false;
  currentIndex: number | null = null;

  constructor(
    private readonly service: MenuService,
    private readonly productService: ProductService,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly cdr: ChangeDetectorRef,
    private readonly builder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.checkRoute();
    this.productForm = this.buildProductForm();
  }

  private initForm() {
    this.form = this.builder.group({
      id: [null],
      name: [null, Validators.required],
      active: [true],
      note: [null],
      theme: [null],
      products: this.builder.array([]),
      restaurantId: ['a12cfd73-fe31-4103-aa47-cf22b8912b19'],
    });
  }

  onCancel(): void {
    this.navigateToList();
  }

  onSave(): void {
    if (this.form.invalid) return;

    if (this.isEdit) {
      this.updateCardapio(this.form.get('id')?.value);
    } else {
      this.createCardapio();
    }
  }

  searchProducts(event: any) {

    const query = event.query;
    const searchs = [];

    if (query) {
      searchs.push(`name=ilike=${query}%`);
    }

    const idsProduct = (this.form.get('products') as FormArray)
      .value
      .map((value: any) => value.product.id)
      .filter((value: any) => value !== '');

    if (idsProduct.length) {
      const ids = idsProduct.join(',');
      searchs.push(`id=out=(${ids})`);
    }

    this.productService.findToMenu(searchs.join(';'))
      .subscribe({
        next: (data) => {
          this.filteredProducts = data;
        },
        error: (err) => {
          console.error('Erro ao buscar produtos', err);
        },
        complete: () => {
          this.loading = false;
        }
      });
  }

  private checkRoute() {

    const { id } = this.route.snapshot.params;
    this.isEdit = id !== 'new';

    if (this.isEdit) {
      this.loadCardapio(id);
      this.service.findProductsInMenu(id)
        .subscribe(data => {
          data.forEach((menuProduct: any) => {
            const productForm = this.buildProductForm();
            productForm.patchValue(menuProduct);
            (this.form.get('products') as FormArray).push(productForm);
          });
        });
    }
  }

  private loadCardapio(id: any): void {
    this.service.findById(id).subscribe({
      next: (cardapio) => this.form.patchValue(cardapio),
      error: () => this.navigateToList(),
    });
  }

  private createCardapio(): void {
    const { id, ...cardapioData } = this.form.value;

    this.service.create(cardapioData).subscribe({
      next: () => this.navigateToList(),
    });
  }

  private updateCardapio(id: string): void {
    this.service.update(id, this.form.value).subscribe({
      next: () => this.navigateToList(),
    });
  }

  protected navigateToList(): void {
    this.router.navigate(['/menu']);
  }

  protected addProduct() {

    const productValue = this.productForm.getRawValue();

    if (this.currentIndex !== null) {
      (this.form.get('products') as FormArray)
        .at(this.currentIndex)
        .patchValue(productValue);
      this.currentIndex = null;
    } else {
      const newProductForm = this.builder.group(productValue);
      (this.form.get('products') as FormArray).push(newProductForm);
    }

    this.productForm.reset();
    this.productForm = this.buildProductForm();
  }

  protected editProduct(index: number) {
    const product = (this.form.get('products') as FormArray).at(index);
    this.productForm.patchValue(product.value);
    this.currentIndex = index;
  }

  protected deleteProduct(index: number) {
    (this.form.get('products') as FormArray).removeAt(index);
  }

  protected clearProduct() {
    this.productForm = this.buildProductForm();
    this.currentIndex = null;
  }

  private buildProductForm() {

    const productForm = this.builder.group({
      id: [''],
      product: [null, Validators.required],
      price: [null, Validators.required],
      active: [true],
    });

    productForm.get('product')?.valueChanges
      .subscribe((product: any) => {

        if (!product?.id)
          return;

        this.productForm.get('price')?.setValue(product.price);
      });

    return productForm;
  }
}
