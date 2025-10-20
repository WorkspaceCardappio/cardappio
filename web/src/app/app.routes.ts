import { Routes } from '@angular/router';

export const routes: Routes = [
    {
      path: 'product',
      children: [
        { path: '', loadComponent: () => import('./pages/product/product-list/product-list.component').then(m => m.ProductComponent)},
        { path: ':id', loadComponent: () => import('./pages/product/product-form/product-form.component').then(m => m.ProductFormComponent)}
      ]
    },
    {
      path: 'category',
      children: [
        { path: '', loadComponent: () => import('./pages/category/list/category-list.component').then(m => m.CategoryListComponent)},
        { path: ':id', loadComponent: () => import('./pages/category/form/category-form.component').then(m => m.CategoryFormComponent)},
      ]
    },
    {
      path: 'ingredient',
      children: [
        { path: '', loadComponent: () => import('./pages/ingredient/ingredient-list/ingredient-list.component').then(m => m.IngredientsComponent)},
        { path: ':id', loadComponent: () => import('./pages/ingredient/ingredients-form/ingredients-form.component').then(m => m.IngredientsFormComponent)},
      ]
    },
    {
      path: 'menu',
      children: [
        { path: '', loadComponent: () => import('./pages/menu/list/menu-list.component').then(m => m.MenuListComponent)},
        { path: ':id', loadComponent: () => import('./pages/menu/form/menu-form.component').then(m => m.MenuFormComponent)},
      ]
    },
    {
      path: 'ticket',
      children: [
        { path: '', loadComponent: () => import('./pages/ticket/list/ticket-list.component').then(m => m.TicketListComponent)},
        { path: ':id', loadComponent: () => import('./pages/ticket/form/ticket-form.component').then(m => m.TicketFormComponent)},
      ]
    },
  {
    path: 'order',
    children: [
      { path: '', loadComponent: () => import('./pages/order/list/order-list.component').then(m => m.OrderListComponent)},
      { path: ':id', loadComponent: () => import('./pages/order/form/order-form.component').then(m => m.OrderFormComponent)},
    ]
  },
  {
    path: 'table',
    children: [
      { path: '', loadComponent: () => import('./pages/table-restaurant/list/table-restaurant-list.component').then(m => m.TableRestaurantListComponent)},
      { path: ':id', loadComponent: () => import('./pages/table-restaurant/form/table-restaurant-form.component').then(m => m.TableRestaurantFormComponent)},
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
  }
];
