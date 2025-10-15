import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
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
import { Observable } from 'rxjs';
import { CategoryService } from '../service/category.service';

@Component({
  selector: 'app-category-form',
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
  providers: [
    CategoryService
  ],
  templateUrl: './category-form.component.html',
  styleUrl: './category-form.component.scss',
})
export class MenuFormComponent implements OnInit {
  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [
    { label: 'Categoria', routerLink: '/category' },
    { label: 'Novo', routerLink: '/category/new' },
  ];

  filteredCategories: any[] = [];
  loading: boolean = false;

  form: FormGroup<any> = new FormGroup({});

  constructor(
    private readonly builder: FormBuilder,
    private service: CategoryService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.checkRoute();
  }

  private initForm() {
    this.form = this.builder.group({
      id: [''],
      name: ['', Validators.required],
      active: [true],
      image: [''],
      parent: [null],
    });
  }

  private checkRoute() {
    const { id } = this.route.snapshot.params;
    if (id != 'new') {
      this.loadCategory(id);
    }
  }

  private loadCategory(id: string) {
    this.service.findById(id)
      .subscribe((category) => this.form.patchValue(category));
  }

  create() {
    if (this.form.invalid) return;

    const { id } = this.route.snapshot.params;

    if (id != 'new') {
      this.service
        .update(id, this.prepareForm())
        .subscribe(() => this.router.navigate(['category']));
    } else {
      this.service
        .create(this.prepareForm())
        .subscribe(() => this.router.navigate(['category']));
    }
  }

  cancel() {
    this.router.navigate(['category']);
  }

  categories = (query: string): Observable<any[]> => {
    const searchs = [];

    if (query) {
      searchs.push(`name=ilike=${query}%`);
    }

    const id = this.form.get('id')?.value;
    if (id) {
      searchs.push(`id=out=${id}`);
    }

    return this.service.findAll(20, searchs.join(';'));
  };

  display = (item: any) => {
    return item.name;
  };

  searchCategories(event: any) {
    const query = event.query;
    const searchs = [];

    if (query) {
      searchs.push(`name=ilike=${query}%`);
    }

    const id = this.form.get('id')?.value;
    if (id) {
      searchs.push(`id=out=${id}`);
    }

    this.service.findAll(20, searchs.join(';')).subscribe({
      next: (data) => {
        this.filteredCategories = data;
      },
      error: (err) => {
        console.error('Erro ao buscar categorias', err);
      },
      complete: () => {
        this.loading = false;
      },
    });
  }

  prepareForm() {
    const raw = this.form.getRawValue();
    return {
      ...raw,
      parent: raw.parent === '' ? null : raw.parent,
    };
  }
}
