import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {
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
import { IconFieldModule } from 'primeng/iconfield';
import { InputTextModule } from 'primeng/inputtext';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { TableRestaurant } from '../model/table-restaurant.type';
import { TableRestaurantService } from '../service/table-restaurant.service';
import { TableStatusService } from '../service/table-status.service';

@Component({
  selector: 'table-restaurant-form-component',
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
  ],
  providers: [TableRestaurantService, TableStatusService],
  templateUrl: 'table-restaurant-form.component.html',
  styleUrl: 'table-restaurant-form.component.scss',
})
export class TableRestaurantFormComponent implements OnInit {
  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [
    { label: 'Mesa', routerLink: '/table' },
    { label: 'Novo', routerLink: '/table/new' },
  ];

  filteredStatus: any[] = [];
  loading: boolean = false;

  form: FormGroup = new FormGroup({});

  constructor(
    private readonly fb: FormBuilder,
    private service: TableRestaurantService,
    private router: Router,
    private route: ActivatedRoute,
    private statusService: TableStatusService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.createForm();

    this.checkMethod();
  }

  onSave(): void {
    if (this.form.invalid) return;

    if (this.isNew) {
      this.create();
      return;
    }

    this.update(this.getId);
  }

  onCancel(): void {
    this.back();
  }

  searchStatus() {
    this.statusService.findAll().subscribe({
      next: (data) => {
        this.filteredStatus = data;
      },
      error: (err) => {
        console.error('Erro ao buscar status', err);
      },
      complete: () => {
        this.loading = false;
        this.cdr.markForCheck();
      },
    });
  }

  get isNew(): boolean {
    return this.getId === 'new';
  }

  get getId() {
    return this.route.snapshot.params['id'];
  }

  private createForm(): void {
    this.form = this.fb.group({
      id: [''],
      number: [null, Validators.required],
      places: [null, Validators.required],
      status: [null, Validators.required],
    });
  }

  private checkMethod() {
    if (!this.isNew) {
      this.loadTable();
    }
  }

  private loadTable(): void {
    const id = this.getId;

    this.service.findById(id).subscribe((table: TableRestaurant) => {
      this.form.patchValue(table);
    });
  }

  private create(): void {
    this.service.create(this.form.getRawValue()).subscribe(() => this.back());
  }

  private update(id: string): void {
    this.service
      .update(id, this.form.getRawValue())
      .subscribe(() => this.back());
  }

  private back(): void {
    this.router.navigate(['table']);
  }
}
