import { Component } from '@angular/core';
import { CancelButtonComponent, GenericButtonComponent, ImageUploadComponent, InputComponent, SaveButtonComponent, ToggleComponent } from "cardappio-component-hub";

@Component({
  selector: 'app-product-form',
  imports: [InputComponent, ToggleComponent, CancelButtonComponent, GenericButtonComponent, ImageUploadComponent, SaveButtonComponent],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.scss'
})
export class ProductFormComponent {

}
