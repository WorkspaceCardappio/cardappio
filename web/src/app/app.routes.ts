import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'category',
        loadComponent: () => import('./components/category/category.component').then(m => m.CategoryComponent)
    }
];
