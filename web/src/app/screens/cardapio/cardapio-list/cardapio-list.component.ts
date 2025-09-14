import { Component } from '@angular/core';
import { ListComponent } from "cardappio-component-hub";
import { CardapioService } from "./cardapio.service";

@Component({
  selector: 'app-cardapio-list',
  imports: [
    ListComponent
  ],
  templateUrl: './cardapio-list.component.html',
  styleUrl: './cardapio-list.component.scss'
})
export class CardapioListComponent {

  listParams = {
    service: this.cardapioService,
    route: '/api/cardapio',
    columns: [
      {
        key: 'name',
        title: 'Nome',
        type: 'text' as const,
        sortable: true,
        filterable: true,
        width: '200px'
      },
      {
        key: 'note',
        title: 'Observação',
        type: 'text' as const,
        sortable: true,
        width: '120px',
        align: 'right' as const
      },
      {
        key: 'theme',
        title: 'Tema',
        type: 'text' as const,
        sortable: true,
        filterable: true,
        width: '150px'
      },
      {
        key: 'status',
        title: 'Status',
        type: 'text' as const,
        sortable: true,
        width: '100px',
        align: 'center' as const
      },
      {
        key: 'actions',
        title: 'Ações',
        type: 'actions' as const,
        width: '120px',
        align: 'center' as const
      }
    ],
    actions: [
      {
        name: 'Editar',
        icon: 'edit',
        color: 'primary',
        action: (item: any) => this.editItem(item)
      },
      {
        name: 'Excluir',
        icon: 'delete',
        color: 'danger',
        action: (item: any) => this.deleteItem(item)
      }
    ],
    filters: [
      {
        key: 'category',
        title: 'Categoria',
        type: 'select',
        options: [
          { label: 'Entradas', value: 'entradas' },
          { label: 'Pratos Principais', value: 'pratos_principais' },
          { label: 'Sobremesas', value: 'sobremesas' },
          { label: 'Bebidas', value: 'bebidas' }
        ]
      },
      {
        key: 'status',
        title: 'Status',
        type: 'select',
        options: [
          { label: 'Ativo', value: 'active' },
          { label: 'Inativo', value: 'inactive' }
        ]
      }
    ]
  };

  constructor(private cardapioService: CardapioService) {}

  ngOnInit() {

  }

  editItem(item: any) {
    console.log('Editar item:', item);
  }

  deleteItem(item: any) {
    if (confirm('Tem certeza que deseja excluir este item?')) {
      console.log('Excluir item:', item);
    }
  }

}
