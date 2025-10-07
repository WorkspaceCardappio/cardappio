import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {
  AutocompleteComponent,
  CancelButtonComponent,
  InputComponent,
  SaveButtonComponent,
} from 'cardappio-component-hub';
import { Observable } from 'rxjs';
import { TableRestaurant } from '../model/table-restaurant.type';
import { TableRestaurantService } from '../service/table-restaurant.service';
import { TableStatusService } from '../service/table-status.service';

@Component({
  selector: 'table-restaurant-form-component',
  imports: [InputComponent,
    ReactiveFormsModule,
    CommonModule,
    CancelButtonComponent,
    SaveButtonComponent, AutocompleteComponent,
  ],
  providers: [TableRestaurantService, TableStatusService],
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
    private tableStatusService: TableStatusService,
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

    this.router.navigate(['table']);
  }

  onCancel(): void {

    this.back();
  }

  private createForm(): void {

    this.form = this.fb.group({
      id: [''],
      number: ['', Validators.required],
      places: ['', Validators.required],
      status: ['', Validators.required],
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

    const formValue = {
      ...this.form.value,
      status: this.form.value.status.code,
    };

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

  tables = (query: string): Observable<any[]> => {

    return this.tableStatusService.findAll();
  }

  display = (item: any) => {
    return item.description;
  }

}
