import { Routes } from '@angular/router';

export const routes: Routes = [

    {
        path: '',
        loadComponent: () => import('./ticket/ticket.component').then(m => m.TicketsComponent)
    },
];