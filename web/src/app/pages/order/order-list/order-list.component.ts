import { Component } from '@angular/core';
import { CardappioListComponent } from "cardappio-component-hub";
import { CardapioService } from "../../cardapio/cardapio-list/cardapio.service";
import { OrderService } from "../order.service";


@Component({
  selector: 'app-order-list',
  imports: [
    CardappioListComponent
  ],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.scss'
})
export class OrderListComponent {

  listParams = {
    service: this.orderService,
    route: '/order',
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
    private orderService: OrderService,
  ) {}

}
