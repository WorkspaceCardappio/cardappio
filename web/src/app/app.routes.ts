import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'category',
        loadComponent: () => import('./pages/category/category.component').then(m => m.CategoryComponent)
    },
    {
         path: 'category/:id',
        loadComponent: () => import('./pages/category/category-form/category-form.component').then(m => m.CategoryFormComponent)
    }
];
