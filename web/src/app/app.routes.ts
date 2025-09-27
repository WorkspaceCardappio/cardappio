import { Routes } from '@angular/router';
import { CardapioListComponent } from "./screens/cardapio/cardapio-list/cardapio-list.component";
import { CardapioFormComponent } from "./screens/cardapio/cardapio-form/cardapio-form.component";

export const routes: Routes = [
  { path:'cardapio-list', component:CardapioListComponent },
  { path:'menus/dto/new', component: CardapioFormComponent },
  { path: 'menus/dto/:id', component: CardapioFormComponent }
];
