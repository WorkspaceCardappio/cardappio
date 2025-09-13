import { Component } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ImageUploadComponent, InputComponent } from 'cardappio-component-hub';

@Component({
  selector: 'app-teste',
  imports: [ InputComponent, ReactiveFormsModule, ImageUploadComponent],
  templateUrl: './teste.component.html',
  styleUrl: './teste.component.scss'
})
export class TesteComponent {
  form!: FormGroup<any>;

}
