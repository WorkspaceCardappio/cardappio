import { Routes } from '@angular/router';

export const routes: Routes = [
    {
      path: 'category',
      children: [
        { path: '', loadComponent: () => import('./pages/category/category/category.component').then(m => m.CategoryComponent)},
        { path: ':id', loadComponent: () => import('./pages/category/category-form/category-form.component').then(m => m.CategoryFormComponent)},
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
        path: 'ingredient',
        children: [
            { path: '', loadComponent: () => import('./pages/ingredient/ingredients/ingredients.component').then(m => m.IngredientsComponent) },
            { path: ':id', loadComponent: () => import('./pages/ingredient/ingredients-form/ingredients-form.component').then(m => m.IngredientsFormComponent) }
        ]
    },
    {
      path: '**',
      loadComponent: () => import('./pages/not-found/not-found.component').then(m => m.NotFoundComponent),
    }
];
