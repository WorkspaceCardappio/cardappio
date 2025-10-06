import { Routes } from '@angular/router';

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
        loadComponent: () => import('./screens/cardapio/cardapio-list/cardapio-list.component').then(m => m.CardapioListComponent)
      },
      {
        path: 'new',
        loadComponent: () => import('./screens/cardapio/cardapio-form/cardapio-form.component').then(m => m.CardapioFormComponent)
      },
      {
        path: ':id',
        loadComponent: () => import('./screens/cardapio/cardapio-form/cardapio-form.component').then(m => m.CardapioFormComponent)
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
      }
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: '**',
    loadComponent: () => import('./pages/not-found/not-found.component').then(m => m.NotFoundComponent),
  },
];
