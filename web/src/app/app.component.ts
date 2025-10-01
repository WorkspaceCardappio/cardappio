import { Component, ChangeDetectionStrategy } from '@angular/core';
import { ModalComponent } from "./modal/modal.component";
import { FormsModule } from "@angular/forms";
import { CancelButtonComponent } from "cardappio-component-hub";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    ModalComponent,
    FormsModule,
    CancelButtonComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {
  onConfirm(): void {
    console.log('Usuário confirmou');
  }

  onCancel(): void {
    console.log('Usuário cancelou');
  }
}
