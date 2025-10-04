import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'ingredient',
        loadComponent: () => import('./pages/ingredient/ingredients/ingredients.component').then(m => m.IngredientsComponent)
    },
    {
        path: 'ingredient/:id',
        loadComponent: () => import('./pages/ingredient/ingredients-form/ingredients-form.component').then(m => m.IngredientsFormComponent)
    },
    {
        path: 'category',
        loadComponent: () => import('./pages/category/category/category.component').then(m => m.CategoryComponent)
    },
    {
         path: 'category/:id',
        loadComponent: () => import('./pages/category/category-form/category-form.component').then(m => m.CategoryFormComponent)
    }
];
