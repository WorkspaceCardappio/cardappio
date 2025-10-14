import { Routes } from '@angular/router';
import { OrderListComponent } from "./pages/order/order-list/order-list.component";

export const routes: Routes = [
  {
    path: 'category',
    children: [
      {
        path: '',
        loadComponent: () => import('./pages/category/category/category.component').then(m => m.CategoryComponent)
      },
      {
        path: ':id',
        loadComponent: () => import('./pages/category/category-form/category-form.component').then(m => m.CategoryFormComponent)
      },
    ]
  },
  {
    path: 'menu',
    children: [
      {
        path: '',
        loadComponent: () => import('./pages/cardapio/cardapio-list/cardapio-list.component').then(m => m.CardapioListComponent)
      },
      {
        path: 'new',
        loadComponent: () => import('./pages/cardapio/cardapio-form/cardapio-form.component').then(m => m.CardapioFormComponent)
      },
      {
        path: ':id',
        loadComponent: () => import('./pages/cardapio/cardapio-form/cardapio-form.component').then(m => m.CardapioFormComponent)
      },
    ]
  },
  {
    path: 'home',
    loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'ticket',
    children: [
      { path: '', loadComponent: () => import('./ticket/ticket.component').then(m => m.TicketsComponent)},
      { path: ':id', loadComponent: () => import('./ticket-form/ticket-form.component').then(m => m.TicketFormComponent)},
    ]
  },
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'order',
    children: [
      {
        path: '',
        loadComponent: () => import('./pages/order/order-list/order-list.component').then(m => m.OrderListComponent)
      },
      {
        path: 'new',
        loadComponent: () => import('./pages/order/order-form/order-form.component').then(m => m.OrderFormComponent)
      },
      {
        path: ':id',
        loadComponent: () => import('./pages/order/order-form/order-form.component').then(m => m.OrderFormComponent)
      },
    ]
  },
  {
    path: '**',
    loadComponent: () => import('./pages/not-found/not-found.component').then(m => m.NotFoundComponent),
  },
];
