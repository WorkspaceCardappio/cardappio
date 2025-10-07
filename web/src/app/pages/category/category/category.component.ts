import { Component } from '@angular/core';
import { CardappioListComponent } from 'cardappio-component-hub';
import { CategoryService } from '../service/category.service';


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
