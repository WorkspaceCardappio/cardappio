import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'product',
        loadComponent: () => import('./pages/product/product/product.component')
            .then(m => m.ProductComponent)
    },
    {
        path: 'product/:id',
        loadComponent: () => import('./pages/product/product-form/product-form.component')
            .then(m => m.ProductFormComponent)
    }
];
