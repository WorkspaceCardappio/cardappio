import { Routes } from '@angular/router';

export const routes: Routes = [

    {
        path: 'ticket',
        children: [
            { path: '', loadComponent: () => import('./ticket/ticket.component').then(m => m.TicketsComponent)},
            { path: ':id', loadComponent: () => import('./ticket-form/ticket-form.component').then(m => m.TicketFormComponent)},
        ]
    }
];