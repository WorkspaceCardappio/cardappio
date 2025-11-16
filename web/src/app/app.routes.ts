import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./pages/register/register.component').then(m => m.RegisterComponent)
  },
  {
    path: 'unauthorized',
    loadComponent: () => import('./pages/unathorized/unathorized.component').then(m => m.UnauthorizedComponent)
  },
  {
    path: '',
    loadComponent: () => import('./layouts/main-layout/main-layout.component').then(m => m.MainLayoutComponent),
    canActivate: [authGuard],
    children: [
      {
        path: 'home',
        loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent)
      },
      {
        path: 'product',
        canActivate: [roleGuard],
        data: {roles: ['USER', 'ADMIN']},
        children: [
          { path: '', loadComponent: () => import('./pages/product/product-list/product-list.component').then(m => m.ProductComponent)},
          { path: ':id', loadComponent: () => import('./pages/product/form/product-form/product-form.component').then(m => m.ProductFormComponent)}
        ]
      },
      {
        path: 'category',
        canActivate: [roleGuard],
        data: {roles: ['USER', 'ADMIN']},
        children: [
          { path: '', loadComponent: () => import('./pages/category/list/category-list.component').then(m => m.CategoryListComponent)},
          { path: ':id', loadComponent: () => import('./pages/category/form/category-form.component').then(m => m.CategoryFormComponent)},
        ]
      },
      {
        path: 'ingredient',
        canActivate: [roleGuard],
        data: {roles: ['USER', 'ADMIN']},
        children: [
          { path: '', loadComponent: () => import('./pages/ingredient/ingredient-list/ingredient-list.component').then(m => m.IngredientsComponent)},
          { path: ':id', loadComponent: () => import('./pages/ingredient/ingredients-form/ingredients-form.component').then(m => m.IngredientsFormComponent)},
        ]
      },
      {
        path: 'menu',
        canActivate: [roleGuard],
        data: {roles: ['USER', 'ADMIN']},
        children: [
          { path: '', loadComponent: () => import('./pages/menu/list/menu-list.component').then(m => m.MenuListComponent)},
          { path: ':id', loadComponent: () => import('./pages/menu/form/menu-form.component').then(m => m.MenuFormComponent)},
        ]
      },
      {
        path: 'ticket',
        canActivate: [roleGuard],
        data: {roles: ['USER', 'ADMIN']},
        children: [
          { path: '', loadComponent: () => import('./pages/ticket/list/ticket-list.component').then(m => m.TicketListComponent)},
          { path: ':id', loadComponent: () => import('./pages/ticket/form/ticket-form.component').then(m => m.TicketFormComponent)},
        ]
      },
      {
        path: 'order',
        canActivate: [roleGuard],
        data: {roles: ['USER', 'ADMIN']},
        children: [
          { path: '', loadComponent: () => import('./pages/order/list/order-list.component').then(m => m.OrderListComponent)},
          { path: ':id', loadComponent: () => import('./pages/order/form/form/order-form.component').then(m => m.OrderFormComponent)},
        ]
      },
      {
        path: 'table',
        canActivate: [roleGuard],
        data: {roles: ['USER', 'ADMIN']},
        children: [
          { path: '', loadComponent: () => import('./pages/table-restaurant/list/table-restaurant-list.component').then(m => m.TableRestaurantListComponent)},
          { path: ':id', loadComponent: () => import('./pages/table-restaurant/form/table-restaurant-form.component').then(m => m.TableRestaurantFormComponent)},
        ]
      },
      {
        path: 'stock',
        canActivate: [roleGuard],
        data: {roles: ['USER', 'ADMIN']},
        children: [
          { path: '', loadComponent: () => import('./pages/stock/stock-list/stock-list.component').then(m => m.StockListComponent)},
          { path: ':id', loadComponent: () => import('./pages/stock/stock-form/stock-form.component').then(m => m.StockFormComponent)},
        ]
      },
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
      },
    ]
  },
  {
    path: '**',
    loadComponent: () => import('./pages/not-found/not-found.component').then(m => m.NotFoundComponent),
  }
];
