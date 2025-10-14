import { Component } from '@angular/core';
import { OrderService } from "../service/order.service";
import { AutoComplete, AutoCompleteCompleteEvent } from "primeng/autocomplete";
import { FormsModule } from "@angular/forms";


@Component({
  selector: 'app-order-list',
  imports: [
    FormsModule,
    AutoComplete
  ],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.scss'
})
export class OrderListComponent {

  items: any[] = [];

  value: any;

  search(event: AutoCompleteCompleteEvent) {
    let _items = [...Array(10).keys()];

    this.items = event.query ? [...Array(10).keys()].map((item) => event.query + '-' + item) : _items;
  }
}
