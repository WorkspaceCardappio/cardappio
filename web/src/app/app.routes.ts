import { Routes } from '@angular/router';
import { CardapioListComponent } from "./screens/cardapio/cardapio-list/cardapio-list.component";
import { CardapioFormComponent } from "./screens/cardapio/cardapio-form/cardapio-form.component";

export const routes: Routes = [
  { path:'cardapio', component:CardapioListComponent },
  { path:'menus/new', component: CardapioFormComponent },
  { path: 'menus/:id', component: CardapioFormComponent }
];
