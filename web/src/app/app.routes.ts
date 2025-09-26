import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'table-restaurant',
    loadComponent: () => import('./pages/table-restaurant/table-restaurant-list/table-restaurant-list.component').then(m => m.TableRestaurantListComponent),
  },
  {
    path: 'table-restaurant/:id',
    loadComponent: () => import('./pages/table-restaurant/table-restaurant-form/table-restaurant-form.component').then(m => m.TableRestaurantFormComponent),
  },
];
