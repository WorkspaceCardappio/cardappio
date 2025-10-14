// import { Component } from '@angular/core';
// import { CardappioListComponent } from "cardappio-component-hub";
// import { OrderService } from "../service/order.service";


// @Component({
//   selector: 'app-order-list',
//   imports: [
//     CardappioListComponent
//   ],
//   templateUrl: './order-list.component.html',
//   styleUrl: './order-list.component.scss'
// })
// export class OrderListComponent {

//   listParams = {
//     service: this.orderService,
//     route: '/order',
//     columns: [
//       {
//         title: 'Número',
//         field: 'number',
//         size: 200,
//         order: 'number'
//       },
//       {
//         title: 'Status',
//         field: 'orderStatus',
//         size: 200,
//         order: 'orderStatus'
//       },
//       {
//         title: 'Preço',
//         field: 'price',
//         size: 150,
//         order: 'price'
//       },
//       {
//         title: 'Data de criação',
//         field: 'createdAt',
//         size: 100,
//         order: 'createdAt'
//       }
//     ],
//     filters: [
//       {
//         icon: 'Comanda',
//         title: 'ticket',
//         value: 'ticket',
//         typeValue: 'string' as const
//       },
//     ]
//   };

//   constructor(
//     private orderService: OrderService,
//   ) {}

// }
