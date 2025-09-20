import { Component } from '@angular/core';
import { CategoryService } from '../service/category.service';
import { CardappioListComponent } from 'cardappio-component-hub';


@Component({
  selector: 'app-category',
  imports: [
    CardappioListComponent
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
