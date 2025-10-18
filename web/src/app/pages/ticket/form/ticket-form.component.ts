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
import { TicketStatusService } from '../../../entity-service/enums/ticket-status.service';
import { TableRestaurantService } from '../../table-restaurant/service/table-restaurant.service';
import { TicketService } from '../service/ticket.service';

@Component({
  selector: 'app-ticket-form',
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
  providers: [TicketService],
  templateUrl: './ticket-form.component.html',
  styleUrl: './ticket-form.component.scss',
})
export class TicketFormComponent implements OnInit {
  home = { icon: 'pi pi-home', routerLink: '/home' };

  items = [
    { label: 'Comanda', routerLink: '/ticket' },
    { label: 'Nova', routerLink: '/ticket/new' },
  ];

  form: FormGroup<any> = new FormGroup({});
  isEdit = false;

  loading = false;
  filteredTables: any[] = [];
  filteredStatus: any[] = [];
  allStatus: any[] = [];

  constructor(
    private readonly service: TicketService,
    private readonly tableService: TableRestaurantService,
    private readonly ticketStatusService: TicketStatusService,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly cdr: ChangeDetectorRef,
    private readonly builder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.findStatus();
    this.checkRoute();
  }

  private initForm() {
    this.form = this.builder.group({
      id: [null],
      number: [null],
      person: [null],
      status: [null, Validators.required],
      table: [null],
      total: [null],
    });
  }

  onCancel(): void {
    this.navigateToList();
  }

  onSave(): void {
    if (this.form.invalid) return;

    if (this.isEdit) {
      this.update(this.form.get('id')?.value);
      return;
    }

    this.create();
  }

  private checkRoute() {
    const { id } = this.route.snapshot.params;
    this.isEdit = id !== 'new';

    if (this.isEdit) {
      this.load(id);
    }
  }

  private load(id: any): void {
    this.service.findById(id).subscribe({
      next: (cardapio: any) => this.form.patchValue(cardapio),
      error: () => this.navigateToList(),
    });
  }

  private create(): void {
    this.service.create(this.form.getRawValue()).subscribe({
      next: () => this.navigateToList(),
    });
  }

  private update(id: string): void {
    this.service.update(id, this.form.getRawValue()).subscribe({
      next: () => this.navigateToList(),
    });
  }

  protected navigateToList(): void {
    this.router.navigate(['/ticket']);
  }

  findStatus() {
    this.ticketStatusService.find().subscribe((data) => {
      this.allStatus = data;
      const statusDefault = this.allStatus.find(
        (status) => status.code === 1
      );

      if (statusDefault) this.form.get('status')?.patchValue(statusDefault);
    });
  }

  filterStatus(event: any) {
    const query = event.query?.toLowerCase() ?? '';

    if (!query) {
      this.filteredStatus = [...this.allStatus];
      return;
    }

    this.filteredStatus = this.allStatus.filter((status) =>
      status.description.toLowerCase().includes(query)
    );
  }

  searchTables(event: any) {
    const query = event.query;
    const searchs = ['status!=UNAVAILABLE'];

    if (query) {
      searchs.push(`number==${query}`);
    }

    this.tableService.findToTicket(searchs.join(';')).subscribe({
      next: (data) => {
        this.filteredTables = data;
      },
      error: (err) => {
        console.error('Erro ao buscar Mesas', err);
      },
      complete: () => {
        this.cdr.markForCheck();
      },
    });
  }
}
