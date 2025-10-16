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
        { path: '', loadComponent: () => import('./pages/cardapio/list/menu-list.component').then(m => m.MenuListComponent)},
        { path: ':id', loadComponent: () => import('./pages/cardapio/form/menu-form.component').then(m => m.MenuFormComponent)},
      ]
    },
    // {
  //   path: 'ticket',
  //   children: [
  //     { path: '', loadComponent: () => import('./pages/ticket/ticket.component').then(m => m.TicketsComponent)},
  //     { path: ':id', loadComponent: () => import('./pages/ticket/ticket-form/ticket-form.component').then(m => m.TicketFormComponent)},
  //   ]
  // },
  // {
  //   path: 'order',
  //   children: [
  //     {
  //       path: '',
  //       loadComponent: () => import('./pages/order/order-list/order-list.component').then(m => m.OrderListComponent)
  //     },
  //     {
  //       path: 'new',
  //       loadComponent: () => import('./pages/order/order-form/order-form.component').then(m => m.OrderFormComponent)
  //     },
  //     {
  //       path: ':id',
  //       loadComponent: () => import('./pages/order/order-form/order-form.component').then(m => m.OrderFormComponent)
  //     },
  //   ]
  // },
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent)
  },
  {
    path: '**',
    loadComponent: () => import('./pages/not-found/not-found.component').then(m => m.NotFoundComponent),
  }
];
