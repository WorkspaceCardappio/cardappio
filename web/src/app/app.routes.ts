import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'ingredient',
        loadComponent: () => import('./pages/ingredients/ingredients.component').then(m => m.IngredientsComponent)
    }
];
