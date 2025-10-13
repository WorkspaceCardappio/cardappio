import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {
  CancelButtonComponent,
  ImageUploadComponent,
  InputComponent,
  SaveButtonComponent,
  ToggleComponent
} from "cardappio-component-hub";
import { CardapioService } from "../cardapio-list/cardapio.service";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: 'app-cardapio-form',
  imports: [
    InputComponent,
    ImageUploadComponent,
    ToggleComponent,
    CancelButtonComponent,
    SaveButtonComponent,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './cardapio-form.component.html',
  styleUrl: './cardapio-form.component.scss'
})
export class CardapioFormComponent implements OnInit {

  cardapioId: string | null = null;
  isEditMode = false;
  cardapioForm: FormGroup;

  constructor(
    private readonly cardapioService: CardapioService,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly cdr: ChangeDetectorRef,
    private readonly fb: FormBuilder
  ) {
    this.cardapioForm = this.fb.group({
      id: [''],
      name: ['', Validators.required],
      active: [true],
      note: [''],
      theme: [''],
      restaurantId: ['a12cfd73-fe31-4103-aa47-cf22b8912b19']
    });
  }

  ngOnInit(): void {
    this.initializeComponent();
  }

  onCancel(): void {
    this.navigateToList();
  }

  onSave(): void {
    if (this.cardapioForm.invalid) return;

    if (this.isEditMode) {
      this.updateCardapio();
    } else {
      this.createCardapio();
    }
  }

  private initializeComponent(): void {
    this.cardapioId = this.route.snapshot.paramMap.get('id');
    this.isEditMode = !!this.cardapioId;

    if (this.isEditMode) {
      this.loadCardapio();
    }
  }

  private loadCardapio(): void {
    this.cardapioService.findById(this.cardapioId).subscribe({
      next: (cardapio) => {
        this.cardapioForm.patchValue({
          id: cardapio.id,
          name: cardapio.name,
          active: cardapio.active ?? true,
          note: cardapio.note,
          theme: cardapio.theme,
          restaurantId: 'a12cfd73-fe31-4103-aa47-cf22b8912b19'
        });
        this.cdr.detectChanges();
      },
      error: () => this.navigateToList()
    });
  }

  private createCardapio(): void {
    const { id, ...cardapioData } = this.cardapioForm.value;

    this.cardapioService.create(cardapioData).subscribe({
      next: () => this.navigateToList()
    });
  }

  private updateCardapio(): void {

    this.cardapioService.update(this.cardapioId, this.cardapioForm.value).subscribe({
      next: () => this.navigateToList()
    });
  }

  protected navigateToList(): void {
    this.router.navigate(['/menu']);
  }
}
