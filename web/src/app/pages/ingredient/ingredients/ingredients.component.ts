import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { CardappioListComponent } from "cardappio-component-hub";
import { IngredientService } from '../service/ingredient.service';

@Component({
  selector: 'app-ingredients',
  imports: [
    CardappioListComponent,
    DatePipe
  ],
  providers: [ IngredientService],
  templateUrl: './ingredients.component.html',
  styleUrl: './ingredients.component.scss'
})
export class IngredientsComponent {
  constructor(public service: IngredientService){}
}
