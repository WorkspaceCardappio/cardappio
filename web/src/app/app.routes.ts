import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'category',
        loadComponent: () => import('./pages/category/category.component').then(m => m.CategoryComponent)
    }
];
