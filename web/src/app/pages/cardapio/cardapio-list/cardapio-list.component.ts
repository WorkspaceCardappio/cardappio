import { Component, OnInit } from '@angular/core';
<<<<<<< HEAD:web/src/app/screens/cardapio/cardapio-list/cardapio-list.component.ts
=======
import { CardapioService } from "../service/cardapio.service";
>>>>>>> f567f0ce0b6a738e14b77bd115e9de2b42c42bb3:web/src/app/pages/cardapio/cardapio-list/cardapio-list.component.ts
import { CardappioListComponent } from "cardappio-component-hub";
import { CardapioService } from "./cardapio.service";

@Component({
  selector: 'app-cardapio-list',
  imports: [
    CardappioListComponent
  ],
  templateUrl: './cardapio-list.component.html',
  styleUrl: './cardapio-list.component.scss'
})
export class CardapioListComponent {

  listParams = {
    service: this.cardapioService,
    route: '/menu',
    columns: [
      {
        title: 'Nome',
        field: 'name',
        size: 200,
        order: 'name'
      },
      {
        title: 'Observação',
        field: 'note',
        size: 200,
        order: 'note'
      },
      {
        title: 'Tema',
        field: 'theme',
        size: 150,
        order: 'theme'
      },
      {
        title: 'Status',
        field: 'active',
        size: 100,
        order: 'status'
      }
    ],
    filters: [
      {
        icon: 'text_fields',
        title: 'Nome',
        value: 'name',
        typeValue: 'string' as const
      },
      {
        icon: 'text_fields',
        title: 'Tema',
        value: 'theme',
        typeValue: 'string' as const
      },
      {
        icon: 'toggle_on',
        title: 'Status',
        value: 'status',
        typeValue: 'string' as const
      }
    ]
  };

  constructor(
    private cardapioService: CardapioService,
  ) {}

}
