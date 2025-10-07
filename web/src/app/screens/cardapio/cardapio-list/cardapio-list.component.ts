import { Component, OnInit } from '@angular/core';
import { CardapioService } from "./cardapio.service";
import { CardappioListComponent } from "cardappio-component-hub";

@Component({
  selector: 'app-cardapio-list',
  imports: [
    CardappioListComponent
  ],
  templateUrl: './cardapio-list.component.html',
  styleUrl: './cardapio-list.component.scss'
})
export class CardapioListComponent implements OnInit {

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

  ngOnInit() {}
}
