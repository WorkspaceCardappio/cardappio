import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'ingredient',
        loadComponent: () => import('./pages/ingredients/ingredients.component').then(m => m.IngredientsComponent)
    },
    {
        path: 'ingredient/:id',
        loadComponent: () => import('./pages/ingredients-form/ingredients-form.component').then(m => m.IngredientsFormComponent)
    }
];
