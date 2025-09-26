import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        loadComponent: () => import('./ticket/ticket.component').then(m => m.TicketComponent)
    },
    {
        path: 'ticket-details/:id',
        loadComponent: () => import('./ticket/ticket-details/ticket-details.component').then(m => m.TicketDetailsComponent)
    }
];
