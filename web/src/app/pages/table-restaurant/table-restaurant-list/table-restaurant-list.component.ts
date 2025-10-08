import { Component } from '@angular/core';
import { CardappioListComponent } from 'cardappio-component-hub';
import { TableRestaurantService } from '../service/table-restaurant.service';
import { iterator } from "rxjs/internal/symbol/iterator";


@Component({
  selector: 'table-restaurant-list-component',
  imports: [
    CardappioListComponent,
  ],
  providers: [
    TableRestaurantService,
  ],
  templateUrl: 'table-restaurant-list.component.html',
  styleUrl: 'table-restaurant-list.component.scss',
})
export class TableRestaurantListComponent {

  constructor(
    public service: TableRestaurantService,
  ) {
  }

  protected readonly iterator = iterator;
}
