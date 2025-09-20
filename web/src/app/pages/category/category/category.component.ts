import { Component } from '@angular/core';
import { CategoryService } from '../service/category.service';
import { CardappioComponent } from 'cardappio-component-hub';


@Component({
  selector: 'app-category',
  imports: [
    CardappioComponent
  ],
  providers: [
    CategoryService
  ],
  templateUrl: './category.component.html',
  styleUrl: './category.component.scss'
})
export class CategoryComponent {
  constructor(public service: CategoryService) {
  }
}
