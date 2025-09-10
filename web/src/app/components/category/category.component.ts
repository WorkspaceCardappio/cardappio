import { Component } from '@angular/core';
import { ListComponent } from 'cardappio-component-hub';
import { CategoryService } from './service/category.service';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [
    ListComponent
  ],
  templateUrl: './category.component.html',
  styleUrl: './category.component.scss'
})
export class CategoryComponent {
  constructor(public service: CategoryService) {
  }
}
