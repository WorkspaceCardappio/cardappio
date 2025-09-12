import { Routes } from '@angular/router';

import { CardapioListComponent } from "./screens/cardapio/cardapio-list/cardapio-list.component";
import { TestComponent } from "./screens/cardapio/cardapio-form/cardapio-form.component";

export const routes: Routes = [
  { path: 'cardapio-form', component: TestComponent },
  { path:'cardapio-list', component: CardapioListComponent}
  ];
