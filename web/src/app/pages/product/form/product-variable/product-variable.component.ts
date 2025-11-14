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
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { VariableService } from '../../../variable/variable.service';

@Component({
  selector: 'app-product-variable',
  imports: [
    InputNumberModule,
    ButtonModule,
    TableModule,
    ReactiveFormsModule,
    InputTextModule,
    ToggleSwitchModule,
    CurrencyPipe
  ],
  templateUrl: './product-variable.component.html',
  styleUrl: './product-variable.component.scss',
})
export class ProductVariableComponent implements OnInit {

  @Input({ required: true }) productId!: string;
  @Output() prevEmitter: EventEmitter<void> = new EventEmitter();
  @Output() finalizeEmitter: EventEmitter<void> = new EventEmitter();

  variablesToSave: any[] = [];

  form: FormGroup<any> = new FormGroup({});

  constructor(
    private readonly service: VariableService,
    private readonly builder: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.form = this.createAdditionalFormGroup();
  }

  createAdditionalFormGroup(): FormGroup {
    const form = this.builder.group({
      id: [''],
      product: [this.productId],
      name: ['', Validators.required],
      price: ['', Validators.compose([Validators.required, Validators.min(1)])],
      active: [true]
    });

    return form;
  }

  addVariable() {
    this.variablesToSave.push(this.form.getRawValue());
    this.form = this.createAdditionalFormGroup();
    this.cdr.detectChanges();
  }

  deleteVariable(index: number) {
    this.variablesToSave.splice(index, 1);
  }

  onSave(){
    this.service.persistItems(this.variablesToSave)
      .subscribe(() => this.finalizeEmitter.emit());
  }

}
