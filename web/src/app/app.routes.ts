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
    path: 'table',
    children: [
      {
        path: '',
        loadComponent: () => import('./pages/table-restaurant/table-restaurant-list/table-restaurant-list.component').then(m => m.TableRestaurantListComponent)
      },
      {
        path: ':id',
        loadComponent: () => import('./pages/table-restaurant/table-restaurant-form/table-restaurant-form.component').then(m => m.TableRestaurantFormComponent)
      },
      {
        path: 'new',
        loadComponent: () => import('./pages/table-restaurant/table-restaurant-form/table-restaurant-form.component').then(m => m.TableRestaurantFormComponent)
      },
    ]
  },
  {
    path: 'home',
    loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent)
  },
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
