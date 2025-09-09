import { Routes } from '@angular/router';
import { CardapioFormComponent } from "./screens/cardapio/cardapio-form/cardapio-form.component";
import { CardapioListComponent } from "./screens/cardapio/cardapio-list/cardapio-list.component";

export const routes: Routes = [

  { path: 'cardapio-form', component: CardapioFormComponent },
  {path:'cardapio-list', component: CardapioListComponent}

  ];
