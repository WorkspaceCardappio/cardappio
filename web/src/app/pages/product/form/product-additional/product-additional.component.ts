import { CurrencyPipe } from '@angular/common';
import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AutoComplete } from 'primeng/autocomplete';
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { AdditionalService } from '../../../additional/additional.service';
import { ProductService } from '../../service/product.service';

@Component({
  selector: 'app-product-additional',
  imports: [
    AutoComplete,
    InputNumberModule,
    ButtonModule,
    TableModule,
    ReactiveFormsModule,
    InputTextModule,
    ToggleSwitchModule,
    CurrencyPipe
  ],
  templateUrl: './product-additional.component.html',
  styleUrl: './product-additional.component.scss',
})
export class ProductAdditionalComponent implements OnInit {
  @Input({ required: true }) productId!: string;
  @Output() prevEmitter: EventEmitter<void> = new EventEmitter();
  @Output() nextEmitter: EventEmitter<void> = new EventEmitter();

  filteredProducts: any[] = [];
  additionalsToSave: any[] = [];
  loading: boolean = true;

  form: FormGroup<any> = new FormGroup({});

  constructor(
    private readonly service: ProductService,
    private readonly additionalService: AdditionalService,
    private readonly builder: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.form = this.createAdditionalFormGroup();
  }

  searchProducts(event: any) {
    const query = event.query;

    let search = '&search=active==TRUE';
    search += query ? `;name=ilike=${query}` : '';

    const completeParams = `pageSize=20;${search}`;

    this.service.findAllDTO(completeParams).subscribe({
      next: (data) => {
        this.filteredProducts = data.content;
      },
      error: (err) => {
        console.error('Erro ao buscar ingredientes', err);
      },
      complete: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  createAdditionalFormGroup(): FormGroup {
    const form = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      product: [this.productId],
      productAdditional: ['', Validators.required],
      price: ['', Validators.compose([Validators.required, Validators.min(1)])],
      active: [true]
    });
    return form;
  }

  addAdditional() {
    this.additionalsToSave.push(this.form.getRawValue());
    this.form = this.createAdditionalFormGroup();
    this.cdr.detectChanges();
  }

  deleteAdditional(index: number) {
    this.additionalsToSave.splice(index, 1);
  }

  onSave(){
    this.additionalService.persistItems(this.additionalsToSave)
      .subscribe(() => this.nextEmitter.emit());
  }

}
