import {CommonModule} from '@angular/common';
import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {CancelButtonComponent, InputComponent, SaveButtonComponent} from 'cardappio-component-hub';
import {TableRestaurant} from '../model/table-restaurant.type';
import {TableRestaurantService} from '../service/table-restaurant.service';

@Component({
  selector: 'table-restaurant-form-component',
  imports: [InputComponent,
    ReactiveFormsModule,
    CommonModule,
    CancelButtonComponent,
    SaveButtonComponent,
  ],
  providers: [TableRestaurantService],
  templateUrl: 'table-restaurant-form.component.html',
  styleUrl: 'table-restaurant-form.component.scss',
})
export class TableRestaurantFormComponent implements OnInit {

  form: FormGroup = new FormGroup({});

  constructor(
    private readonly fb: FormBuilder,
    private service: TableRestaurantService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {

    this.createForm();

    this.checkMethod();
  };

  onSave(): void {

    const { id } = this.route.snapshot.params;

    if (this.form.invalid) {

      return;
    }

    if (this.isNew) {

      this.createTable();

      return;
    }

    this.updateTable(id);
  }

  private back(): void {

    this.router.navigate(['table-restaurant']);
  }

  onCancel() {

    this.back();
  }

  private createForm(): void {

    this.form = this.fb.group({
      id: [''],
      number: ['', Validators.required],
      status: ['', Validators.required],
      places: ['', Validators.required],
    });
  }

  private checkMethod() {

    if (!this.isNew) {

      this.loadTable();
    }
  }

  private loadTable(): void {

    const id = this.route.snapshot.params['id'];

    this.service.findById(id).subscribe((table: TableRestaurant) => {
      this.form.patchValue(table);
    });
  }

  private createTable(): void {

    this.service.create(this.form.value).subscribe(() =>
      this.back()
    );
  }

  private updateTable(id: string): void {

    this.service.update(id, this.form.value).subscribe(() =>
      this.back()
    );
  }

  get isNew(): boolean {

    return this.route.snapshot.params['id'] === 'new';
  }

}
