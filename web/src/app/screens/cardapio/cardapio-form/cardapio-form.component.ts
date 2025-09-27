import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {
  CancelButtonComponent,
  ImageUploadComponent,
  InputComponent,
  SaveButtonComponent,
  ToggleComponent
} from "cardappio-component-hub";
import { CardapioService } from "../cardapio-list/cardapio.service";
import { FormsModule } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: 'app-cardapio-form',
  imports: [
    InputComponent,
    ImageUploadComponent,
    ToggleComponent,
    CancelButtonComponent,
    SaveButtonComponent,
    FormsModule
  ],
  templateUrl: './cardapio-form.component.html',
  styleUrl: './cardapio-form.component.scss'
})
export class CardapioFormComponent implements OnInit {

  cardapioId: string | null = null;
  isEditMode = false;

  cardapio = {
    id: '',
    name: '',
    active: true,
    note: '',
    theme: '',
    restaurantId: 'a12cfd73-fe31-4103-aa47-cf22b8912b19'
  };

  constructor(
    private readonly cardapioService: CardapioService,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly cdr: ChangeDetectorRef

  ) {}

  ngOnInit(): void {
    this.initializeComponent();
  }

  onCancel(): void {
    this.navigateToList();
  }

  onSave(): void {
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
        this.cardapio = {
          id: cardapio.id || '',
          name: cardapio.name || '',
          active: cardapio.active ?? true,
          note: cardapio.note || '',
          theme: cardapio.theme || '',
          restaurantId: cardapio.restaurantId || 'a12cfd73-fe31-4103-aa47-cf22b8912b19'
        };
        this.cdr.detectChanges();
      },
      error: () => this.navigateToList()
    });
  }

  private createCardapio(): void {
    this.cardapioService.create(this.cardapio).subscribe({
      next: () => this.navigateToList(),
      error: (error) => console.error('Erro ao criar cardápio:', error)
    });
  }

  private updateCardapio(): void {
    if (!this.cardapioId) return;


    this.cardapioService.update(this.cardapioId, this.cardapio).subscribe({
      next: () => this.navigateToList(),
      error: (error) => console.error('Erro ao atualizar cardápio:', error)
    });
  }

  private navigateToList(): void {
    this.router.navigate(['/cardapio-list']);
  }
}
